package com.company.project.biz.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.io.*;
import java.util.List;

/**
 * @author: chenyin
 * @date: 2019-03-07 23:18
 */
public class JedisUtil {
    private Logger logger = LoggerFactory.getLogger(JedisUtil.class);
    private JedisPool jedisPool;

    public JedisUtil(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String get(String key) {
        String value = null;
        Jedis jedis = null;

        try {
            jedis = this.getResource();
            if (jedis.exists(this.getRuleKey(key))) {
                value = jedis.get(this.getRuleKey(key));
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
                this.logger.debug("get {} = {}", this.getRuleKey(key), value);
            }
        } catch (Exception var8) {
            this.logger.warn("get {} = {}", new Object[]{this.getRuleKey(key), value, var8});
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return value;
    }

    public String set(String key, String value) {
        return this.set(key, value, 0);
    }

    public String set(String key, String value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;

        try {
            jedis = this.getResource();
            result = jedis.set(this.getRuleKey(key), value);
            if (cacheSeconds != 0) {
                jedis.expire(this.getRuleKey(key), cacheSeconds);
            }

            this.logger.debug("set {} = {}", this.getRuleKey(key), value);
        } catch (Exception var10) {
            this.logger.warn("set {} = {}", new Object[]{this.getRuleKey(key), value, var10});
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return result;
    }

    public String setAtomic(String key, String value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;

        try {
            jedis = this.getResource();
            result = jedis.setex(this.getRuleKey(key), cacheSeconds, value);
            this.logger.debug("set {} = {}", this.getRuleKey(key), value);
        } catch (Exception var10) {
            this.logger.warn("set {} = {}", new Object[]{this.getRuleKey(key), value, var10});
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return result;
    }

    public String setnxAtomic(String key, String value, int cacheSeconds) {
        if (cacheSeconds < 0) {
            throw new IllegalArgumentException();
        }

        String result = null;
        Jedis jedis = null;

        try {
            jedis = this.getResource();
            result = jedis.set(this.getRuleKey(key), value, "nx", "ex", cacheSeconds);
            this.logger.debug("set {} = {}", this.getRuleKey(key), value);
        } catch (Exception var10) {
            this.logger.warn("set {} = {}", new Object[]{this.getRuleKey(key), value, var10});
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return result;
    }

    public long del(String key) {
        long result = 0L;
        Jedis jedis = null;

        try {
            jedis = this.getResource();
            if (jedis.exists(this.getRuleKey(key))) {
                result = jedis.del(this.getRuleKey(key));
                this.logger.debug("del {}", this.getRuleKey(key));
            } else {
                this.logger.debug("del {} not exists", this.getRuleKey(key));
            }
        } catch (Exception var9) {
            this.logger.warn("del {}", this.getRuleKey(key), var9);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return result;
    }

    public long getExpireTTL(String key) {
        Jedis jedis = null;
        Long ttlValue = null;

        long var4;
        try {
            if (!StringUtils.isBlank(key)) {
                jedis = this.getResource();
                ttlValue = jedis.ttl(this.getRuleKey(key));
                return null == ttlValue ? 0L : ttlValue;
            }

            var4 = ttlValue;
        } catch (Exception var9) {
            this.logger.error("getExpireTTL {} ", this.getRuleKey(key), var9);
            return null == ttlValue ? 0L : ttlValue;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Long expire(String key, int second) {
        Jedis jedis = null;

        try {
            Long var4;
            if (!StringUtils.isBlank(key)) {
                jedis = this.getResource();
                var4 = jedis.expire(this.getRuleKey(key), second);
                return var4;
            }

            var4 = null;
            return var4;
        } catch (Exception var8) {
            this.logger.error("expire {}", this.getRuleKey(key), var8);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return null;
    }

    public Long incrWithExpire(String key, int second) {
        Jedis jedis = null;

        try {
            Long result;
            if (!StringUtils.isBlank(key)) {
                jedis = this.getResource();
                result = jedis.incr(this.getRuleKey(key));
                if (second > 0) {
                    jedis.expire(this.getRuleKey(key), second);
                }

                Long var5 = result;
                return var5;
            }

            result = null;
            return result;
        } catch (Exception var9) {
            this.logger.error("incr {} ", this.getRuleKey(key), var9);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return null;
    }

    public Long incrByWithExpire(String key, Long integer, int second) {
        Jedis jedis = null;

        try {
            Long result;
            if (StringUtils.isBlank(key)) {
                result = null;
                return result;
            }

            if (integer == null) {
                integer = 0L;
            }

            jedis = this.getResource();
            result = jedis.incrBy(this.getRuleKey(key), integer);
            if (second > 0) {
                jedis.expire(this.getRuleKey(key), second);
            }

            Long var6 = result;
            return var6;
        } catch (Exception var10) {
            this.logger.error("incr {} ", this.getRuleKey(key), var10);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return null;
    }

    public Long incr(String key) {
        Jedis jedis = null;

        Long var3;
        try {
            if (!StringUtils.isBlank(key)) {
                jedis = this.getResource();
                var3 = jedis.incr(this.getRuleKey(key));
                return var3;
            }

            var3 = null;
        } catch (Exception var7) {
            this.logger.error("incr {} ", this.getRuleKey(key), var7);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public Long incrBy(String key, Long integer) {
        Jedis jedis = null;

        try {
            Long var4;
            if (!StringUtils.isBlank(key)) {
                if (integer == null) {
                    integer = 0L;
                }

                jedis = this.getResource();
                var4 = jedis.incrBy(this.getRuleKey(key), integer);
                return var4;
            }

            var4 = null;
            return var4;
        } catch (Exception var8) {
            this.logger.error("incr {} ", this.getRuleKey(key), var8);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return null;
    }

    public Long decrBy(String key, Long integer) {
        Jedis jedis = null;

        try {
            Long var4;
            if (!StringUtils.isBlank(key)) {
                if (integer == null) {
                    integer = 0L;
                }

                jedis = this.getResource();
                var4 = jedis.decrBy(this.getRuleKey(key), integer);
                return var4;
            }

            var4 = null;
            return var4;
        } catch (Exception var8) {
            this.logger.error("incr {} ", this.getRuleKey(key), var8);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return null;
    }

    public Long decr(String key) {
        Jedis jedis = null;

        Long var3;
        try {
            if (StringUtils.isBlank(key)) {
                var3 = null;
                return var3;
            }

            jedis = this.getResource();
            var3 = jedis.decr(this.getRuleKey(key));
        } catch (Exception var7) {
            this.logger.error("decr {} ", this.getRuleKey(key), var7);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public void lpush(String key, String value) {
        Jedis jedis = null;

        try {
            if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
                jedis = this.getResource();
                jedis.lpush(this.getRuleKey(key), new String[]{value});
                return;
            }
        } catch (Exception var8) {
            this.logger.error("rpush {} = {}", new Object[]{this.getRuleKey(key), value, var8});
            return;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public void rpush(String key, String value) {
        Jedis jedis = null;

        try {
            if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
                return;
            }

            jedis = this.getResource();
            jedis.rpush(this.getRuleKey(key), new String[]{value});
        } catch (Exception var8) {
            this.logger.error("rpush {} = {}", new Object[]{this.getRuleKey(key), value, var8});
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public String lpop(String key) {
        Jedis jedis = null;

        String var3;
        try {
            if (StringUtils.isBlank(key)) {
                var3 = null;
                return var3;
            }

            jedis = this.getResource();
            var3 = jedis.lpop(this.getRuleKey(key));
        } catch (Exception var7) {
            this.logger.error("lpop {}", this.getRuleKey(key), var7);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public boolean exists(String key) {
        boolean result = false;
        Jedis jedis = null;

        try {
            jedis = this.getResource();
            result = jedis.exists(this.getRuleKey(key));
        } catch (Exception var8) {
            this.logger.warn("exists {}", this.getRuleKey(key), var8);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return result;
    }

    private Jedis getResource() throws JedisException {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            return jedis;
        } catch (JedisException var3) {
            this.logger.warn("getResource.", var3);
            throw var3;
        }
    }

    public Object getFromMap(String type, String key) {
        Jedis jedis = this.getJedis();

        Object var5;
        try {
            List<byte[]> data = jedis.hmget(type.getBytes(), new byte[][]{this.getRuleKey(key).getBytes()});
            if (data != null && !data.isEmpty()) {
                var5 = deserialize((byte[]) data.get(0));
                return var5;
            }

            var5 = null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Boolean putMap(String type, String key, Object data) throws Exception {
        if (data == null) {
            return false;
        } else {
            Jedis jedis = this.getJedis();

            Boolean var5;
            try {
                jedis.hset(type.getBytes(), this.getRuleKey(key).getBytes(), serialize(data));
                var5 = true;
            } finally {
                if (jedis != null) {
                    jedis.close();
                }

            }

            return var5;
        }
    }

    private static byte[] serialize(Object value) {
        if (value == null) {
            return null;
        } else {
            byte[] result = null;
            ByteArrayOutputStream bos = null;
            ObjectOutputStream os = null;

            try {
                bos = new ByteArrayOutputStream();
                os = new ObjectOutputStream(bos);
                os.writeObject(value);
                os.close();
                bos.close();
                result = bos.toByteArray();
            } catch (IOException var8) {
                throw new IllegalArgumentException("Non-serializable object", var8);
            } finally {
                close((OutputStream) os);
                close((OutputStream) bos);
            }

            return result;
        }
    }

    private static Object deserialize(byte[] in) {
        Object result = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;

        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                result = is.readObject();
                is.close();
                bis.close();
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        } catch (ClassNotFoundException var10) {
            var10.printStackTrace();
        } finally {
            close((InputStream) is);
            close((InputStream) bis);
        }

        return result;
    }

    private static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

    }

    private static void close(OutputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

    }

    public long setnx(String key, String value, int cacheSeconds) {
        Jedis jedis = null;
        long result = 0L;

        try {
            jedis = this.getResource();
            result = jedis.setnx(this.getRuleKey(key), value);
            if (cacheSeconds != 0) {
                jedis.expire(this.getRuleKey(key), cacheSeconds);
            }

            this.logger.debug("setnx {} = {}", this.getRuleKey(key), value);
        } catch (Exception var11) {
            this.logger.warn("setnx {} = {}", new Object[]{this.getRuleKey(key), value, var11});
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return result;
    }

    public String getSet(String key, String value) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = this.getResource();
            result = jedis.getSet(this.getRuleKey(key), value);
            this.logger.debug("getSet {} = {}", this.getRuleKey(key), value);
        } catch (Exception var9) {
            this.logger.warn("getSet {} = {}", new Object[]{this.getRuleKey(key), value, var9});
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return result;
    }

    private Jedis getJedis() {
        Jedis jedis = this.jedisPool.getResource();
        return jedis;
    }

    public JedisPool getJedisPool() {
        return this.jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    private String getRuleKey(String key) {
        return key;
    }
}
