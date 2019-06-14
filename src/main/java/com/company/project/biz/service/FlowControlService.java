package com.company.project.biz.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * @author: chenyin
 * @date: 2019-06-06 14:32
 */
@Service
public class FlowControlService {

    @SentinelResource("clusterFlowControl")
    public int clusterFlowControlTest(int i) {
        return i;
    }
}
