package com.company.project.configurer;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.company.project.biz.util.IpUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyin
 * @date: 2019-07-21 13:13
 */
@Configuration
public class SentinelConfig implements InitializingBean {
    @Bean
    public RequestOriginParser requestOriginParser() {
        RequestOriginParser requestOriginParser = new RequestOriginParser() {
            @Override
            public String parseOrigin(HttpServletRequest request) {
                String ip = IpUtil.getClientIP(request);
//                System.out.println("当前请求:"+ip);
                return ip;
            }

        };
        return requestOriginParser;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DubboFallbackRegistry.setConsumerFallback(new DubboFallback() {
            @Override
            public Result handle(Invoker<?> invoker, Invocation invocation, BlockException e) {
                return null;
            }
        });


        DubboFallbackRegistry.setProviderFallback(new DubboFallback() {
            @Override
            public Result handle(Invoker<?> invoker, Invocation invocation, BlockException e) {
                return null;
            }
        });
    }
}
