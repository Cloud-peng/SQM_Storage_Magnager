package com.smq.eduservice.service;

import com.smq.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smq.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-09
 */
public interface EduCourseService extends IService<EduCourse> {
    //    添加课程基本信息
    void saveCourseInfo(CourseInfoVo courseInfoVo);
}
