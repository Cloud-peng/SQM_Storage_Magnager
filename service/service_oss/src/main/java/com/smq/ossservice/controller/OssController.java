//package com.smq.ossservice.controller;
//
//import cn.hutool.core.date.DateTime;
//import cn.xuyanwu.spring.file.storage.FileInfo;
//import cn.xuyanwu.spring.file.storage.FileStorageService;
//import com.smq.commonutils.R;
//import com.smq.ossservice.utils.ConstantPropertiesUtil;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.UUID;
//
///**
// * @author 彭云
// * @title: OssController
// * @projectName guli_parent
// * @description: TODO
// * @date 2023/6/300:59
// */
//@Api(description = "头像上传")
//@RestController
//@CrossOrigin//处理跨域
//@RequestMapping("/eduoss/fileoss")
//public class OssController {
//    @Autowired
//    private FileStorageService fileStorageService;//注入实列
//
//
////    @Autowired
////    private OssService ossService;
//////        上传头像的方法
////    @PostMapping("/uploadavatar")
////    public R UpLoadAvator(MultipartFile file){
//////        获取上传文件
//////        返回上传到oss的路径
////        String url=ossService.UpLoadFileAvatar(file);
////        return R.ok().data("url",url);
////    }
//    String filePath=new DateTime().toString("yyyy/MM/dd");
//    InputStream is;
//    @PostMapping("uploadavator")
//    public R upload(MultipartFile  file) throws IOException {
//        String  orgfileName=file.getOriginalFilename();
//        String  contentType = file.getContentType();
//        String  savaName=UUID.randomUUID().toString().replaceAll("-","")+orgfileName;
////            System.out.println("contentType-->"+contentType);
////            System.out.println("savaName-->"+savaName);
//            try {
//                is = file.getInputStream();
//                FileInfo fileInfo = fileStorageService.of(is)
//                        .setSaveFilename(savaName)
//                        .setContentType(contentType)
//                        .setPath(filePath + "/") //保存到相对路径下，为了方便管理，不需要可以不写
////                .setObjectId("0")   //关联对象id，为了方便管理，不需要可以不写
////                .setObjectType("0") //关联对象类型，为了方便管理，不需要可以不写
////                .putAttr("role","admin") //保存一些属性，可以在切面、保存上传记录、自定义存储平台等地方获取使用，不需要可以不写
//                        .upload();  //将文件上传到对应地方
//                is.close();
//                String url = ConstantPropertiesUtil.END_POINT + ConstantPropertiesUtil.BUCKET_NAME + fileInfo.getUrl();
////                System.out.println(url);
//                return fileInfo==null ? R.error().message("文件不能为空"): R.ok().data("url",url);
//            }catch (IOException e){
//                return null;
//            }
//
//    }
//
//    /**
//     * 上传文件到指定存储平台，成功返回文件信息
//     */
//    @PostMapping("uploadfiles")
//    public FileInfo uploadPlatform(MultipartFile file) {
//        return fileStorageService.of(file)
//                .setPlatform("minio-1")    //使用指定的存储平台
//                .upload();
//    }
//}
