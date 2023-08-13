package com.smq.eduservice.controller;


import com.smq.commonutils.R;
import com.smq.eduservice.entity.vo.CourseInfoVo;
import com.smq.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-07-09
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    EduCourseService eduCourseService;
//    添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo ){
        eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok();
    }

}

