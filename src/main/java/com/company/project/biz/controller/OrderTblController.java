package com.company.project.biz.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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
        IPage<OrderTbl> page = orderTblMapper.selectPage(new Page<>(1, 1), new QueryWrapper<>());

        return new BaseResult(page);
    }

    public static void main(String[] args) {
        String[] str = new String[] { "yang", "hao" };
        List<String> list = Arrays.asList(str);
        str[0] = "chenyin";
        System.out.println(list);
    }
}
