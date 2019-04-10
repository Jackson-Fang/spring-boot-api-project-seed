package com.company.project.biz.controller;


import com.company.project.bean.BaseResult;
import com.company.project.biz.entity.OrderTbl;
import com.company.project.biz.mapper.OrderTblMapper;
import com.company.project.biz.service.OrderTblService;
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
public class OrderTblController {
    @Resource
    private OrderTblService orderTblService;
    @Resource
    private OrderTblMapper orderTblMapper;
    @GetMapping("test")
    public BaseResult test() {
        OrderTbl orderTbl = new OrderTbl().setMoney(10).setCount(2).setCommodityCode("22").setUserId("userId");
        orderTblService.save(orderTbl);
        return new BaseResult(orderTbl);
    }

}
