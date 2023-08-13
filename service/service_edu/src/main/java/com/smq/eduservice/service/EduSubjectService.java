package com.smq.eduservice.service;

import com.smq.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smq.eduservice.entity.subject.OneSubjcet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-02
 */
public interface EduSubjectService extends IService<EduSubject> {
//    添加课程分类
    void saveSubject(MultipartFile file);

    List<OneSubjcet> gerAllOneTwoSubject();
}
