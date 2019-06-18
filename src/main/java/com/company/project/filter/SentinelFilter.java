package com.company.project.filter;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.company.project.bean.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhangyu on 2019/2/27 下午2:04.
 */
@Component
@Slf4j
public class SentinelFilter extends CommonFilter {

    @Override
    public void init(FilterConfig filterConfig) {
//        WebCallbackManager.setUrlBlockHandler(new UrlBlockHandler() {
//            @Override
//            public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse response, BlockException e) throws IOException {
//                PrintWriter writer = null;
//                try {
//                    BaseResult baseResult = new BaseResult(BaseResult.FAILED, "系统繁忙,请稍后重试");
//                    response.setContentType("text/html;charset=utf-8");
//                    writer = response.getWriter();
//                    writer.write(JSON.toJSONString(baseResult));
//                    writer.flush();
//                } finally {
//                    if (null != writer) {
//                        writer.close();
//                    }
//                }
//            }
//        });
//        super.init(filterConfig);
    }
}
