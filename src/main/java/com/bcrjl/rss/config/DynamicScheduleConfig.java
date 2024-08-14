package com.bcrjl.rss.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;
import com.bcrjl.rss.job.RssJob;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.PeriodicTrigger;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

import static com.bcrjl.rss.common.constant.AppConstant.REFRESH;

/**
 * 动态配置订阅
 *
 * @author yanqs
 * @since 2024-08-06
 */
@Data
@Slf4j
@Configuration
@EnableScheduling
public class DynamicScheduleConfig implements SchedulingConfigurer {

    @Resource
    private RssJob rssJob;

    /**
     * 默认五分钟执行一次
     */
    private Long timer = 5L * 1000L * 60L;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                rssJob.subscribe();
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                log.info("当前执行速度：{}分钟", timer / 1000L / 60L);
                PeriodicTrigger periodicTrigger = new PeriodicTrigger(timer);
                Date nextExecutionTime = periodicTrigger.nextExecutionTime(triggerContext);
                return nextExecutionTime;
            }
        });
    }
}
