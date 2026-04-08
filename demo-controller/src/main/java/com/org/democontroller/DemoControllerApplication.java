package com.org.democontroller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.org.democontroller",   // 你的 controller
        "com.org.demoservice",      // 你的 service impl
        "com.org.demomapper",      // 你的 mapper
        "com.org.demoentity",      // 实体类
        "com.org.democommon"       // 通用包
})
@MapperScan("com.org.demomapper") // 扫描 Mapper 接口
public class DemoControllerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoControllerApplication.class, args);
    }
}
