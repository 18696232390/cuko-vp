package org.cuko.cloud.test.nacos.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单服务
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * 下单
     * @return
     */
    @RequestMapping("/")
    public String hello(){
        return System.currentTimeMillis()+"hello";
    }
}