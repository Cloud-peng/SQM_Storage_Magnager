package com.smq.eduservice.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smq.eduservice.entity.EduSubject;
import com.smq.eduservice.entity.excel.SubjectData;
import com.smq.eduservice.service.EduSubjectService;
import com.smq.servicebase.exceptionhandler.GuliException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 彭云
 * @title: SubjectExcelListener
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/7/222:26
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

//    因为SubjectExcelListener不能交给Spring进行管理，需要自己new,不能注入其他对象，不能实现数据库操作
    //创建有参数构造，传递subjectService用于操作数据库
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    public SubjectExcelListener() {
    }

    private EduSubjectService eduSubjectService;
//     一行行读取，每次读取有两个值。第一个值一级分类，第二个值二级分类
//    判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String title){
        QueryWrapper<EduSubject> wrapper= new QueryWrapper<>();
        wrapper.eq("title",title);
        wrapper.eq("parent_id",0);
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);
        return oneSubject;
    }

    //    判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String title,String pid){
        QueryWrapper<EduSubject> wrapper= new QueryWrapper<>();
        wrapper.eq("title",title);
        wrapper.eq("parent_id",pid);
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);
        return oneSubject;
    }

//    一行行读取数据
    @Transactional
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
    if (subjectData==null){
        throw new GuliException(20001,"数据为空");
    }
    //   判断一级分类是否重复
    EduSubject existOneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubectName());
    if (existOneSubject==null){//没有相同一级分类进行添加
        existOneSubject=new EduSubject();
        existOneSubject.setParentId("0");
        existOneSubject.setTitle(subjectData.getOneSubectName());
        eduSubjectService.save(existOneSubject);
    }
    //   判断二级分类是否重复
//        获取一级分类的id值
    String pid=existOneSubject.getId();
    EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService,subjectData.getTwoSubectName(),pid  );
    if (existTwoSubject==null){//没有相同的二级分类进行添加
        existTwoSubject=new EduSubject();
        existTwoSubject.setParentId(pid);
        existTwoSubject.setTitle(subjectData.getTwoSubectName());
        eduSubjectService.save(existTwoSubject);
    }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
