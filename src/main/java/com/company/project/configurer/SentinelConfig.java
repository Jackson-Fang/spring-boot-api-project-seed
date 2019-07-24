package com.company.project.configurer;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.company.project.biz.util.IpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyin
 * @date: 2019-07-21 13:13
 */
@Configuration
public class SentinelConfig {
    @Bean
    public RequestOriginParser requestOriginParser() {
        RequestOriginParser requestOriginParser = new RequestOriginParser() {
            @Override
            public String parseOrigin(HttpServletRequest request) {
                String ip = IpUtil.getClientIP(request);
                System.out.println("当前请求:"+ip);
                return ip;
            }

        };
        return requestOriginParser;
    }
}
