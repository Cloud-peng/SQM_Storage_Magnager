package com.smq.eduservice.controller;


import com.smq.commonutils.R;
import com.smq.eduservice.entity.subject.OneSubjcet;
import com.smq.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-07-02
 */

@RestController
@CrossOrigin
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;
    @ApiOperation(value = "excel文件解析上传")
    @PostMapping("addsubject")
    public R addSubject(MultipartFile file){
    //      上传过来excel文件
        eduSubjectService.saveSubject(file);
        return R.ok();
    }
//    课程分类列表(树形)
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubjcet>  list=eduSubjectService.gerAllOneTwoSubject();
        return  R.ok().data("list",list);
    }

}

