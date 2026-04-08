package com.org.democontroller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.org.demomapper") // 扫描 Mapper 接口
public class DemoControllerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoControllerApplication.class, args);
    }
}
