package com.company.project.biz.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.company.project.bean.BaseResult;
import com.company.project.biz.util.IpUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: chenyin
 * @date: 2019-06-06 14:32
 */
@Service
public class FlowControlService {

    @SentinelResource(value ="apiLimit",blockHandler = "handleFlowException", blockHandlerClass = {ExceptionUtil.class})
    public int mockApiLimit(int i)  {
        return i;
    }

    @SentinelResource(value ="paramFlowRules",blockHandler = "handleParamFlowException", blockHandlerClass = {ExceptionUtil.class})
    public BaseResult mockParamFlowControl(String userName) {
        System.out.println("当前请求热点用户名:" + userName);
        return new BaseResult();
    }
    @SentinelResource(value ="authoritylowRules",blockHandler = "handleAuthorityFlowException", blockHandlerClass = {ExceptionUtil.class})
    public BaseResult mockAuthorityFlowControl() {
        return new BaseResult();
    }

    @SentinelResource(value ="ipRules",blockHandler = "handleIpFlowException", blockHandlerClass = {ExceptionUtil.class})
    public BaseResult ipLimitTest(HttpServletRequest httpServletRequest) {
        String ip = IpUtil.getClientIP(httpServletRequest);
        System.out.println("ip限流测试，请求成功，当前ip:" + ip);
        return new BaseResult();
    }


}
