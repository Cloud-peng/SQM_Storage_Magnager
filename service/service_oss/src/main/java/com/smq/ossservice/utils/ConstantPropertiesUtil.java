package com.smq.ossservice.utils;

/**
 * @author 彭云
 * @title: ConstantPropertiesUtil
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/6/300:50
 */

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 常量类，读取配置文件application.properties中的配置
 */
@Component
@Configuration
//@PropertySource("classpath:application.properties")
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${spring.minio.endpoint}")
    private String endpoint;
    @Value("${spring.minio.access-key}")
    private String accesskey;
    @Value("${spring.minio.secret-key}")
    private String secretkey;
    @Value("${spring.minio.bucketname}")
    private String bucketname;
    public static String END_POINT;
    public static String ACCESS_KEY;
    public static String SECRET_KEY;
    public static String BUCKET_NAME;
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY = accesskey;
        SECRET_KEY = secretkey;
        BUCKET_NAME= bucketname;
    }
}