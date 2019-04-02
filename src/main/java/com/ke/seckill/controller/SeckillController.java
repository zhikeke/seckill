package com.ke.seckill.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ke.seckill.constant.GoodKey;
import com.ke.seckill.constant.SeckillUserKey;
import com.ke.seckill.dto.SeckillGoodDTO;
import com.ke.seckill.entity.OrderMessage;
import com.ke.seckill.entity.Orders;
import com.ke.seckill.entity.SeckillOrders;
import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.limit.AccessLimit;
import com.ke.seckill.redis.RedisService;
import com.ke.seckill.response.Response;
import com.ke.seckill.response.ResponseMessage;
import com.ke.seckill.service.IGoodsService;
import com.ke.seckill.service.ISeckillGoodsService;
import com.ke.seckill.service.ISeckillOrdersService;
import com.ke.seckill.service.ISeckillService;
import com.ke.seckill.service.impl.RabbitMQSender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    // 存放秒杀结束的商品
    private List<Long> seckillOverGoods = new ArrayList<>();

    private static char[] ops = new char[] {'+', '-', '*'};

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private ISeckillOrdersService seckillOrdersService;
    @Autowired
    private ISeckillService seckillService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RabbitMQSender rabbitMQSender;

    /**
     * 秒杀
     * @param model
     * @param user
     * @param goodId 商品id
     * @return
     */
    @RequestMapping("/{path}/do_seckill")
    @ResponseBody
    public Response doSeckill(Model model, SeckillUser user, @RequestParam("goodId") long goodId,
                              @RequestParam("verifyCode") int verifyCode, @PathVariable("path") String path) {
         if (null == user) {
             return Response.error(ResponseMessage.SESSION_ERROR);
         }

         // 校验path
         String userPath = redisService.get(SeckillUserKey.SECKILL_PATH, user.getId() + "_" + goodId, String.class);

        if (StringUtils.isBlank(path) || !path.equals(userPath)) {
            return Response.error(ResponseMessage.REQUEST_ILLEGAL);
        }

        // 校验验证码
        if (!checkVerifyCode(user.getId(), goodId, verifyCode)) {
            return Response.error(ResponseMessage.VERIFY_CODE_ERROR);
        }

         // 内存标记，直接内存判断是否在秒杀结束商品列表中,减少redis流量
         if (seckillOverGoods.contains(goodId)) {
             return Response.error(ResponseMessage.SECKILL_OVER);
         }

         // 判断是否有已经存在秒杀订单
        SeckillOrders seckillOrders = seckillOrdersService.selectOrderByUserIdAndGoodId(user.getId(), goodId);

         if (null != seckillOrders) {
             return Response.error(ResponseMessage.REPEATE_SECKILL);
         }

         // 判断库存,直接走redis, 如果为空或少于等于0,直接返回秒杀结束
        Long stock = redisService.get(GoodKey.SECKILL_GOODS_STOCK, String.valueOf(goodId), Long.class);

         if (null == stock || stock <= 0) {
             seckillOverGoods.add(goodId);
             return Response.error(ResponseMessage.SECKILL_OVER);
         }

         // 判断时间
        Date currentTime = new Date();
        Long seckillStartTime = redisService.get(GoodKey.SECKILL_GOODS_START_TIME, String.valueOf(goodId), Long.class);
        Long seckillEndTime = redisService.get(GoodKey.SECKILL_GOODS_END_TIME, String.valueOf(goodId), Long.class);

        if (currentTime.getTime() < seckillStartTime || currentTime.getTime() > seckillEndTime) {
            return Response.error(ResponseMessage.SECKILL_NOT_IN_TIME);
        }

        // 减库存,如果减完库存少于0,返回秒杀失败
        Long remainStock = redisService.decr(GoodKey.SECKILL_GOODS_STOCK, String.valueOf(goodId));

        if (remainStock < 0) {
            return Response.error(ResponseMessage.SECKILL_OVER);
        }

        // 发送生产订单消息到MQ
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setGoodId(goodId);
        orderMessage.setUserId(user.getId());
        rabbitMQSender.sendSaveOrderMessage(orderMessage);

        return Response.success(0);

//        SeckillGoodDTO good = seckillGoodsService.getDetailById(goodId);
//
//         if (null != good) {
//             long currentTime = System.currentTimeMillis();
//
//             // 库存不足或时间超时， 提示秒杀结束
//             if (good.getStock() <= 0 || currentTime > good.getEndDate().getTime()) {
//                 return Response.error(ResponseMessage.SECKILL_OVER);
//             }
//
//             if (currentTime < good.getStartDate().getTime()) {
//                 return Response.error(ResponseMessage.SECKILL_NOT_START);
//             }
//
//             // 判断是否已经存在秒杀的订单
//             SeckillOrders seckillOrder = seckillOrdersService.selectOrderByUserIdAndGoodId(user.getId(), goodId);
//
//             if (null != seckillOrder) {
//                 return Response.error(ResponseMessage.REPEATE_SECKILL);
//             }
//
//             // 减库存， 下订单， 写入秒杀订单
//             Orders order = seckillService.seckill(user, good);
//
//             if (null != order) {
//                 return Response.success(order);
//             }
//
//             return Response.error(ResponseMessage.SECKILL_OVER);
//         }
//
//        return Response.error(ResponseMessage.SECKILL_OVER);
    }


    /**
     * 查询秒杀结果
     * @param goodId 商品id
     * @param seckillUser
     * @return
     */
    @RequestMapping("/result")
    @ResponseBody
    public Response getSeckillResult(@RequestParam("goodId") long goodId, SeckillUser seckillUser) {
        if (null == seckillUser) {
            return Response.error(ResponseMessage.SESSION_ERROR);
        }

        SeckillOrders seckillOrders = seckillOrdersService.selectOrderByUserIdAndGoodId(seckillUser.getId(), goodId);

        if (null == seckillOrders) {
            // 直接查数据库是否还有商品库存
            boolean hasStock = seckillGoodsService.getStockById(goodId);

            // 如果商品还有库存，提示秒杀等待中
            if (hasStock) {
                return Response.success(0);
            }

            // 没有库存又找不到订单提示秒杀失败
            return Response.success(-1);
        }

        return Response.success(seckillOrders.getOrderId());
    }

    /**
     * 获取用户秒杀路径
     * @param user
     * @param goodId 商品id
     * @return
     */
    @AccessLimit(seconds = 5, accessCount = 3, needLogin = true)
    @RequestMapping("/getSeckillPath")
    @ResponseBody
    public Response getSeckillPath(SeckillUser user, @RequestParam("goodId") long goodId) {
        if (null == user) {
            return Response.error(ResponseMessage.SESSION_ERROR);
        }

        String path = UUID.randomUUID().toString().replaceAll("-", "");
        redisService.set(SeckillUserKey.SECKILL_PATH, user.getId() + "_" + goodId, path);

        return Response.success(path);
    }

    /**
     * 获取验证码图片
     * @param response
     * @param user
     * @param goodId
     */
    @AccessLimit
    @RequestMapping("/verifyCode")
    @ResponseBody
    public void getVerifyCode(HttpServletResponse response, SeckillUser user, @RequestParam("goodId") long goodId) {
        if (null != user && goodId > 0) {
            BufferedImage verifyImage = createVerifyCode(user.getId(), goodId);

            OutputStream outputStream = null;
            // 将图片写回前端
            try {
                outputStream = response.getOutputStream();
                ImageIO.write(verifyImage, "JPEG", outputStream);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private BufferedImage createVerifyCode(long userId, long goodsId) {
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }

        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int res = calc(verifyCode);
        redisService.set(SeckillUserKey.SECKILL_VERIFY_CODE_RESULT, userId + "_" + goodsId, res);

        //输出图片
        return image;
    }

    /**
     * 验证图形验证码结果，验证成功删除redis记录
     * @param userId 用户id
     * @param goodId 商品id
     * @param verifyCode 验证码结果
     * @return
     */
    private boolean checkVerifyCode(long userId, long goodId, int verifyCode) {
        Integer resRedis = redisService.get(SeckillUserKey.SECKILL_VERIFY_CODE_RESULT, userId + "_" + goodId, Integer.class);

        if(resRedis == null || resRedis - verifyCode != 0 ) {
            return false;
        }

        redisService.delete(SeckillUserKey.SECKILL_VERIFY_CODE_RESULT, userId + "_" + goodId);
        return true;
    }

    /**
     * 计算表达式结果
     * @param exp
     * @return
     */
    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * + - *
     */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }
}
