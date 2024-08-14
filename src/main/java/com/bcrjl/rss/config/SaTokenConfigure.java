package com.bcrjl.rss.config;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 配置
 *
 * @author yanqs
 */
@Configuration
public class SaTokenConfigure {
    /**
     * Sa-Token 整合 jwt (Simple 简单模式)
     *
     * @return
     */
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }
}
