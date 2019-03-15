package com.ke.seckill.controller;

import com.ke.seckill.entity.SeckillUser;
import com.ke.seckill.service.ISeckillUserService;
import com.ke.seckill.util.MD5Util;
import com.ke.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@Controller
public class FileController {
    @Autowired
    private ISeckillUserService userService;

    @RequestMapping("/create")
    @ResponseBody
    public String create() {
        for (int i = 0; i < 10; i++) {
            final int a = i;
            new Thread(() -> {
                createUser("15622973" + a);
            }).start();
        }

        return "success";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(HttpServletResponse response) {
        for (int i = 0; i < 10; i++) {
            final int a = i;
            new Thread(() -> {
                login(response, "15622973" + a);
            }).start();
        }

        return "success";
    }

    private void createUser(String pref) {
        for (int i = 0; i < 100; i++) {
            SeckillUser user = new SeckillUser();
            StringBuilder mobile = new StringBuilder(pref);

            if (i < 10) {
                mobile.append("00").append(i);
            } else {
                mobile.append("0").append(i);
            }

            user.setMobile(mobile.toString());
            user.setSalt("kekeda");
            user.setPassword(MD5Util.inputPassToDbPass("123456", user.getSalt()));
            user.setHead("kekeda");
            user.setDeleted(0);
            user.setLoginCount(1);
            user.setRegisterDate(new Date());
            user.setLastLoginDate(new Date());

            userService.createUser(user);
        }
    }


    private void login(HttpServletResponse response, String pref) {
        File file = new File("D:\\token.txt");
        FileOutputStream outputStream = null;

        try {
             outputStream = new FileOutputStream(file);

            for (int i = 0; i < 100; i++) {
                StringBuilder mobile = new StringBuilder(pref);

                if (i < 10) {
                    mobile.append("00").append(i);
                } else {
                    mobile.append("0").append(i);
                }

                LoginVo loginVo = new LoginVo();
                loginVo.setMobile(mobile.toString());
                loginVo.setPassword(MD5Util.inputPassToFormPass("123456"));

                String token = userService.login(response, loginVo);

                outputStream.write(token.getBytes());
                outputStream.write("\r\n".getBytes());// 写入一个换行
            }

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
