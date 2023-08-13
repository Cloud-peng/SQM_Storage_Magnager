//package com.atguigu.ossservice.config;
//
//import io.minio.MinioClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author 彭云
// * @title: MinioConfig
// * @projectName guli_parent
// * @description: TODO
// * @date 2023/6/3023:19
// */
//@Configuration
//public class MinioConfig {
//    @Autowired
//    MinioProperties minioProperties;
//    @Bean
//    public MinioClient minioClient(){
//        MinioClient minioClient =
//                MinioClient.builder()
//                        .endpoint(minioProperties.getEndpoint())
//                        .credentials(minioProperties.getAccesskey(), minioProperties.getSecretkey())
//                        .build();
//        return minioClient;
//    }
//}
