package com.company.project.biz.util;

import com.alibaba.fastjson.JSON;
import com.company.project.bean.LoginUser;
import com.company.project.core.ProjectConstant;
import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenyin
 */
@Service("userUtilService")
public class UserUtilService {
//    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private JedisUtil jedisUtil;

    /**
     * 根据token获取当前用户信息
     *
     * @param token
     * @return
     */
    public LoginUser getLoginUserInfo(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String redisValue = jedisUtil.get(token);
        if (StringUtils.isBlank(redisValue)) {
            return null;
        }
        LoginUser loginUser = JSON.parseObject(redisValue, LoginUser.class);
        if (null == loginUser) {
            jedisUtil.del(token);
        }
        return loginUser;
    }

    /**
     * 设置登录用户信息
     *
     * @param token
     * @param loginUser
     */
    public void setLoginUserInfo(String token, LoginUser loginUser) {
        if (StringUtils.isBlank(token) || null == loginUser) {
            return;
        }
        String userInfo = JSON.toJSONString(loginUser);
        jedisUtil.set(token, userInfo, ProjectConstant.TOKEN_TIMEOUT);
    }

    /**
     * 随机生成cookie
     *
     * @return
     */
    public String getGenerateToken(Long userId) {
        String loginToken = UUIDUtil.generateShort32Uuid();
        loginToken = userId + "_" + loginToken;
        return loginToken;
    }

}
