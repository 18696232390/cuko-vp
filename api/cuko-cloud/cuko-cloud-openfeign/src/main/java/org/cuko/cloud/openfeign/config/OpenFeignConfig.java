package org.cuko.cloud.openfeign.config;

import feign.Logger;
import feign.Request;
import org.cuko.cloud.openfeign.interceptor.CustomFeignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局配置：当使用@Configuration 会将配置作用所有的服务提供方
 * 局部配置：如果只想针对某一个服务进行配置，就不要加@Configuration
 */
@Configuration
public class OpenFeignConfig {

    @Bean
     public Logger.Level feignLoggerLevel(){
         return Logger.Level.FULL;
     }

    /**
     * 超时时间配置
     * @return
     */
    @Bean
    public Request.Options options(){
        return new Request.Options(5000,10000);
    }

    /**
     * 自定义feign拦截器
     * @return
     */
    @Bean
    public CustomFeignInterceptor customFeignInterceptor() {
        return new CustomFeignInterceptor();
    }
}