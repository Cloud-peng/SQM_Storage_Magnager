package com.smq.eduservice.controller;


import com.smq.commonutils.R;
import com.smq.eduservice.entity.EduTeacher;
import com.smq.eduservice.entity.vo.TeacherQuery;
import com.smq.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-05-13
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAllTeacher")
    public R getAllTeacher(){
//        System.out.printf("findAllTeacher被访问了");
//        调用service的方法实现查询的所有的操作
        List<EduTeacher> list = eduTeacherService.list(null);
//        try {
//            int a=10/0;
//        }catch (Exception e){
//            throw  new GuliException(20001,"执行了自定义异常处理");
//        }
        return R.ok().data("total",list.size()).data("items",list);
    }

//   逻辑删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
//    分页查询讲师的方法
    //current当前页
    //limit每页显示记录数
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit){
//        创建page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
//        调用方法实现分页
//        调用方法的时候，底层实现封装，把分页所有数据封装到pageTeacher对象里面
        eduTeacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

//        Map map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);//第一种返回值写法

        return R.ok().data("total",total).data("rows",records);
    }

//    条件查询分页的方法
    @PostMapping ("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) TeacherQuery teacherQuery){
//        创建Page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
//        构建条件
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
//         多条件组合查询

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //        判断条件是否为空,如果不为空拼接条件
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.like("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.gt("gmt_modified",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
//      排序
        wrapper.orderByDesc("gmt_create");
//        调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

//    添加讲师接口的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

//    根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return  R.ok().data("teacher",eduTeacher);
    }
//    讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

