package com.company.project.util;

import com.company.project.bean.SpringBean;

/**
 * jedis分布式锁
 */
public class JedisLockUtil {
    /**
     * 获取jedis对象
     *
     * @return
     */
    private static JedisUtil getJedisUtil() {
        return SpringBean.getBean(JedisUtil.class);
    }

    /**
     * 加锁
     *
     * @param lockKey
     * @param timeOut
     * @return
     */
    public static boolean lock(String lockKey, Integer timeOut) {
        String result =  getJedisUtil().setnxAtomic(lockKey, "1", timeOut);
        return "ok".equalsIgnoreCase(result);
    }

    public static boolean lock(String lockKey, String value, Integer timeOut) {
        String result =  getJedisUtil().setnxAtomic(lockKey, value, timeOut);
        return "ok".equalsIgnoreCase(result);
    }

    /**
     * 删除锁
     *
     * @param lockKey
     */
    public static void del(String lockKey) {
        getJedisUtil().del(lockKey);
    }

    public static String get(String lockKey) {
        return getJedisUtil().get(lockKey);
    }

}
