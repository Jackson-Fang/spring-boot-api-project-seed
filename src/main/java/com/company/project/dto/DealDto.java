package com.company.project.dto;

import lombok.Data;

/**
 * @author: chenyin
 * @date: 2018/12/15 下午12:48
 */
@Data
public class DealDto {
    /**
     * 综合
     */
    private String zonghe;
    /**
     * 熟悉
     */
    private String attribute;
    /**
     * 成交额
     */
    private Double dealMoney;

    /**
     * 交易时间
     */
    private String dealTime;

    /**
     * 交易比例
     */
    private String dealPercent;

}
