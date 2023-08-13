package com.smq.eduservice.service.impl;

import com.smq.eduservice.entity.EduCourse;
import com.smq.eduservice.entity.EduCourseDescription;
import com.smq.eduservice.entity.vo.CourseInfoVo;
import com.smq.eduservice.mapper.EduCourseMapper;
import com.smq.eduservice.service.EduCourseDescriptionService;
import com.smq.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smq.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-09
 */
@Service
@Transactional

public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    //    添加课程基本信息的方法
    @Autowired
    private   EduCourseDescriptionService eduCourseDescriptionService;
        @Override
        public void saveCourseInfo(CourseInfoVo courseInfoVo) {
        //保存课程基本信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
            boolean save = this.save(eduCourse);
        //    也可写为    baseMapper.insert(eduCourse);
            if (save){//     成功
                String cid=eduCourse.getId();
                //     保存课程详情信息
                EduCourseDescription eduCourseDescription=new EduCourseDescription();
                BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
                eduCourseDescription.setId(cid);
                eduCourseDescriptionService.save(eduCourseDescription);
            }else {
        //      失败
                throw  new GuliException(20001,"失败");
            }




    }
}
