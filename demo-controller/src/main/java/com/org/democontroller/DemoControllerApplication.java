package com.org.democontroller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

/*
  多模块Spring程序启动类需要扫描所有模块的组件和Mapper接口，否则会导致无法注入Service和Mapper，出现空指针异常。
  通过@ComponentScan注解指定扫描的包路径，确保所有模块的组件都能被Spring容器管理。
  同时通过@MapperScan注解指定扫描Mapper接口的包路径，确保MyBatis能够正确识别和注入Mapper接口。
  这样配置后，Spring Boot应用程序就能够正确加载和管理所有模块的组件和Mapper接口，避免了空指针异常的发生。
  需要注意的是，@ComponentScan注解的basePackages属性需要包含所有模块的包路径，以确保所有组件都能被扫描到。
  同时，@MapperScan注解的value属性需要指定包含Mapper接口的包路径，以确保MyBatis能够正确识别和注入Mapper接口。
  综上所述，通过正确配置@ComponentScan和@MapperScan注解，可以解决多模块Spring程序启动时出现的空指针异常问题，
  确保所有模块的组件和Mapper接口都能被正确加载和管理。
 */

@ComponentScan(basePackages = {
        "com.org.democontroller",
        "com.org.demoservice",
        "com.org.demomapper",
        "com.org.demoentity",
        "com.org.democommon"
})
@MapperScan("com.org.demomapper")
public class DemoControllerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoControllerApplication.class, args);
    }
}
