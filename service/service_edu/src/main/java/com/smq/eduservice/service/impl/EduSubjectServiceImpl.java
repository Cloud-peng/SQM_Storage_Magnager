package com.smq.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smq.eduservice.entity.EduSubject;
import com.smq.eduservice.entity.excel.SubjectData;
import com.smq.eduservice.entity.subject.OneSubjcet;
import com.smq.eduservice.entity.subject.TwoSubject;
import com.smq.eduservice.entity.subject.TwoSubject;
import com.smq.eduservice.listener.SubjectExcelListener;
import com.smq.eduservice.mapper.EduSubjectMapper;
import com.smq.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Autowired
    private EduSubjectService eduSubjectService;
//    添加课程分类
    @Override
    public void saveSubject(MultipartFile file) {
        try{
            InputStream is=file.getInputStream();
//           文件输入流
            EasyExcel.read(is, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubjcet> gerAllOneTwoSubject() {
//查询所有一级分类 parentid=0
        QueryWrapper<EduSubject> queryWrapperOne=new QueryWrapper<>();
        queryWrapperOne.eq("parent_id","0");
//      baseMapper查询
        List<EduSubject> oneeduSubjectList = baseMapper.selectList(queryWrapperOne);
//        this查询
//        this.list(queryWrapperOne);


        //查询所有二级分类 parentid=0
        QueryWrapper<EduSubject> queryWrapperTwo=new QueryWrapper<>();
        queryWrapperTwo.ne("parent_id","0");
//      baseMapper查询
        List<EduSubject> twoeduSubjectList = baseMapper.selectList(queryWrapperTwo);
//        this查询
//        this.list(queryWrapperTwo);


//        创建存储集合用于存储最终封装的数据
        List<OneSubjcet> finalSubjectList=new ArrayList<>();
//        封装一级分类
//        查询出所有的一级分类list集合遍历，得到每一个一级分类对象，获取每一个一级分类对象值
//        封装到要求的List集合里面，List<OneSubjcet> finalSubjectList
        for (EduSubject oneeduSubject:oneeduSubjectList//遍历oneeduSubjectList集合
             ) {
//            把eduSubject里面值取出来,放到OneSubject对象里面
            OneSubjcet oneSubjcet = new OneSubjcet();
//            oneSubjcet.setId(eduSubject.getId());
//            oneSubjcet.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(oneeduSubject,oneSubjcet);

//            在一级分类循环中遍历查询所有的二级分类
//            创建集合封装所有一级分类的二级分类
            List<TwoSubject> twoFinalSubjectlist=new ArrayList<>();
            for (EduSubject twoeduSubject: twoeduSubjectList
                 ) {
                if (twoeduSubject.getParentId().equals(oneeduSubject.getId())){
//                    把twoeduSubject的值复制到TwoSubject里面，放到twoFinalSubjectlist里面
                    TwoSubject twoSubject=new TwoSubject();
                    BeanUtils.copyProperties(twoeduSubject,twoSubject);
                    twoFinalSubjectlist.add(twoSubject);
                }
            }
            oneSubjcet.setChildren(twoFinalSubjectlist);

            finalSubjectList.add(oneSubjcet);
        }

//        封装二级分类


        return finalSubjectList;
    }

}
