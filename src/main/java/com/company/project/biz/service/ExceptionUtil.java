package com.company.project.biz.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.company.project.bean.BaseResult;

/**
 * @author: chenyin
 * @date: 2019-07-17 10:26
 */
public final class ExceptionUtil {
    public static int handleFlowException(int str, BlockException ex) {
        System.out.println("api限流触发: " + ex.getClass().getCanonicalName());
        return 0;
    }
    public static BaseResult handleParamFlowException(String userName, BlockException ex) {
        System.out.println("热点限流触发: " + ex.getClass().getCanonicalName() + "，限流用户" + userName);
        return new BaseResult(BaseResult.FAILED,"请求过于频繁");
    }
    public static BaseResult handleAuthorityFlowException(BlockException ex) {
        System.out.println("授权限流触发: " + ex.getClass().getCanonicalName());
        return new BaseResult(BaseResult.FAILED,"请求过于频繁");
    }
}
