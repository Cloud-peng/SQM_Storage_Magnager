//package com.atguigu.ossservice.service.serviceImpl;
//
//import com.atguigu.ossservice.config.MinioProperties;
//import com.atguigu.ossservice.service.OssService;
//import com.atguigu.ossservice.utils.ConstantPropertiesUtil;
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//import io.minio.errors.MinioException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.UUID;
//
///**
// * @author 彭云
// * @title: OssServiceImpl
// * @projectName guli_parent
// * @description: TODO
// * @date 2023/6/300:58
// */
//@Service
//public class OssServiceImpl implements OssService {
//
//    private  String url;
//    @Autowired
//    private MinioClient minioClient;
//    @Autowired
//    private MinioProperties minioProperties;
//
////    单个文件上传
//    @Override
//    public String UpLoadFileAvatar(MultipartFile multipartFile) {
//        if(multipartFile==null||multipartFile.isEmpty()){
//            return null;
//        }
//        try{
////          创建文件读取流
//            InputStream in=multipartFile.getInputStream();
//            String orgfileName=multipartFile.getOriginalFilename();
//            String storefileName= UUID.randomUUID().toString().replaceAll("-","")+orgfileName;
//            minioClient.putObject(
//                    PutObjectArgs.builder().bucket(minioProperties.getBucketname()).object(storefileName).stream(
//                                    in, multipartFile.getSize(), -1)
//                            .contentType(multipartFile.getContentType())
//                            .build());
////           关闭流
//            in.close();
//            url=minioProperties.getEndpoint()+storefileName;
//        }catch (IOException | MinioException | InvalidKeyException | NoSuchAlgorithmException e){
//
//        }
//        return url;
//    }
//}
