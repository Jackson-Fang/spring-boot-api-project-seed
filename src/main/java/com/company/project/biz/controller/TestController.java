package com.company.project.biz.controller;

import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.company.project.bean.BaseResult;
import com.company.project.biz.service.FlowControlService;
import com.company.project.biz.util.IpUtil;
import com.ywwl.trial.goods.client.TbUnionAdzoneClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chenyin
 * @since 2019-04-10
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController{

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService
                = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(
                () -> System.out.println("执行任务"), 3000, 1000, TimeUnit.MILLISECONDS);
    }

}
