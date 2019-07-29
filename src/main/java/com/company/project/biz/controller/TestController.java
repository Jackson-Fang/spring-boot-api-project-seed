package com.company.project.biz.controller;

import com.company.project.bean.BaseResult;
import com.company.project.biz.service.FlowControlService;
import com.ywwl.trial.goods.client.TbUnionAdzoneClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
public class TestController {

    @Resource
    private FlowControlService flowControlService;
    @Resource
    private TbUnionAdzoneClientService tbUnionAdzoneClientService;

    /* ------------- 普通流控 -------------- */
    /**
     * 普通web项目 http接口限流，资源名为requestMapping全路径 /test/flowControl
     * 用于限制web接口qps
     * @return
     */
    @GetMapping("flowControl")
    public BaseResult flowControl() {
        System.out.println("flowControl");
        return new BaseResult();
    }

    /**
     * 模拟 被调用方接口限流接口(如淘宝接口限流)，资源名为sentinelResource注解中value值(apiLimit)
     * 注意需自己实现 blockHandler，即限流时异常处理类，详情参考 ExceptionUtil
     *
     * @return
     */
    @GetMapping("flowControl1")
    public BaseResult flowControl1() {
        for (int i = 0; i < 10; i++) {
            //mock接口调用
            System.out.println(flowControlService.mockApiLimit(i));
            System.out.println("api调用成功" + i);
        }
        return new BaseResult();
    }

    /**
     * 根据来源(如ip，userName，app)限流，用于防止某用户恶意请求
     * 需要实现 RequestOriginParser 从 HttpServletRequest中获取请求来源信息
     * 控制台中修改限流来源为指定origin
     * 详情参考 com.company.project.configurer.SentinelConfig#requestOriginParser()
     * @return
     */
    @GetMapping("flowControl2")
    public BaseResult flowControl2() {
        return new BaseResult();
    }

    /* ------------- 热点参数流控 -------------- */

    /**
     * 根据请求参数进行限流，,根据参数(如ip，userName，app)限流，不同参数qps互不干扰
     * 也可以针对特殊参数值做配置，(用于防止某用户恶意请求等)
     * 例子中针对userName进行限流(公共限流配置)，对userName为 ywwl 时进行特殊限流
     * @param userName
     * @return
     */
    @GetMapping("paramFlowControl")
    public BaseResult paramFlowControl(String userName) {
        return flowControlService.mockParamFlowControl(userName);
    }

    /**
     * 黑名单配置
     * @return
     */
    @GetMapping("blackList")
    public BaseResult blackList() {
        return flowControlService.mockAuthorityFlowControl();
    }

    @GetMapping("dubboConsumer")
    public BaseResult dubboConsumer() {
        String result = tbUnionAdzoneClientService.getTbkAppKey();
        return new BaseResult(result);
    }

}
