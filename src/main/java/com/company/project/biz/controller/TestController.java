package com.company.project.biz.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.bean.BaseResult;
import com.company.project.biz.entity.OrderTbl;
import com.company.project.biz.mapper.OrderTblMapper;
import com.company.project.biz.service.FlowControlService;
import com.company.project.biz.service.OrderTblService;
import com.company.project.biz.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenyin
 * @since 2019-04-10
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController  {
    @Resource
    private OrderTblService orderTblService;
    @Resource
    private OrderTblMapper orderTblMapper;
    @Resource
    private JedisUtil jedisUtil;

    @GetMapping("test")
    public BaseResult test() {
        IPage<OrderTbl> page = orderTblMapper.selectPage(new Page<>(1, 1), new QueryWrapper<>());

        return new BaseResult(page);
    }
    @Resource
    private FlowControlService flowControlService;

    @GetMapping("flowControl")
    public BaseResult flowControl() {
        for (int i = 0; i < 10; i++) {
            //mock接口调用
            System.out.println(flowControlService.clusterFlowControlTest(i));
            System.out.println("请求成功"+i);
        }
        System.out.println("请求成功");
        return new BaseResult();
    }
    @GetMapping("returnTest")
    @SentinelResource("returnTest")
    public BaseResult returnTest() {
        System.out.println("returnTest请求成功"+ TransportConfig.getRuntimePort());
        return new BaseResult();
    }

}
