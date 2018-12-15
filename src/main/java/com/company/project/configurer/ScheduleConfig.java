package com.company.project.configurer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * 定时器配置
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadFactory namedFactory = new ThreadFactoryBuilder().setNameFormat("schedule-pool-%d").build();
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5, namedFactory);
        taskRegistrar.setScheduler(scheduledExecutorService);
    }
}
