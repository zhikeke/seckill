package com.ke.seckill.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author  keke
 * @create  2018/2/12 18:02
 * @desc
 **/
@Service
@Slf4j
public class JedisAdapter implements InitializingBean {
    private Jedis jedis = null;
    private JedisPool jedisPool = null;

    @Value("${jedis.host}")
    private String jedisHost;

    @Value("${jedis.port}")
    private int jedisPort;


    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool(jedisHost, jedisPort);
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        }catch (Exception e) {
            log.error("jedis get 操作 发生异常!!!" + e.getMessage());
            return null;
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
			log.error("jedit set 操作发送异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key, value);
        }catch (Exception e) {
			log.error("jedit sadd 操作时发生异常!!!" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key, value);
        }catch (Exception e) {
			log.error("jedit srem 操作时发生异常!!!" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key, value);
        }catch (Exception e) {
			log.error("jedit sismember 操作时发生异常!!!" + e.getMessage());
            return false;
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        }catch (Exception e) {
			log.error("jedit scard 操作时发生异常!!!" + e.getMessage());
            return 0;
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key, value);
        }catch (Exception e) {
			log.error("jedis lpush 操作发送异常" + e.getMessage());
            return 0;
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
			log.error("jedis brpop 操作时发生异常!!!" + e.getMessage());
            return null;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }




}
