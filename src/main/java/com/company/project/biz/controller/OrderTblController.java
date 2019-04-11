package com.company.project.biz.controller;


import com.company.project.bean.BaseResult;
import com.company.project.biz.entity.OrderTbl;
import com.company.project.biz.mapper.OrderTblMapper;
import com.company.project.biz.service.OrderTblService;
import com.company.project.biz.util.JedisLockUtil;
import com.company.project.biz.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/orderTbl")
@Slf4j
public class OrderTblController {
    @Resource
    private OrderTblService orderTblService;
    @Resource
    private OrderTblMapper orderTblMapper;
    @Resource
    private JedisUtil jedisUtil;

    @GetMapping("test")
    public BaseResult test() {
        log.info("info测试");
        log.error("error测试");


        return new BaseResult();
    }

}
