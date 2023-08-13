package com.smq.itemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 彭云
 * @title: EduApplication
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/5/1313:34
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.smq"})
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class,args);
    }
}