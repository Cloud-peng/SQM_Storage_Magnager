package com.smq.itemservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smq.commonutils.R;
import com.smq.itemservice.entity.SmqItem;
import com.smq.itemservice.entity.vo.ItemQuery;
import com.smq.itemservice.service.SmqItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-07-16
 */
@Api(description = "类目管理")
@RestController
@RequestMapping("/smq/itemservice")
@CrossOrigin
public class SmqItemController {
    @Autowired
    SmqItemService smqItemService;

    @ApiOperation(value = "所有类目列表")
    @GetMapping("findAllitem")
    public R getAllItems() {
        List<SmqItem> list = smqItemService.list(null);
//            smqItemService.list();
        return R.ok().data("total", list.size()).data("item", list);
    }
    @Transactional
    //   逻辑删除类目
    @ApiOperation(value = "逻辑删除类目")
    @DeleteMapping("item/{id}")
    public R removeItem(@PathVariable Long id) {
        boolean flag = smqItemService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //    分页查询类目的方法
    //current当前页
    //limit每页显示记录数
    @ApiOperation(value = "分页类目列表")
    @GetMapping("pageItem/{current}/{limit}")
    public R pageListItem(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit) {
//        创建page对象
        Page<SmqItem> pageItem = new Page<>(current, limit);
//        调用方法实现分页
//        调用方法的时候，底层实现封装，把分页所有数据封装到pageItem对象里面
        smqItemService.page(pageItem,null);

        long total = pageItem.getTotal();//总记录数
        List<SmqItem> records = pageItem.getRecords();//数据list集合
        return R.ok().data("total",total).data("rows",records);
    }
    //    条件查询分页的方法
    @ApiOperation(value = "条件查询分页类目列表")
    @PostMapping ("pageItemCondition/{current}/{limit}")
    public R pageItemCondition(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) ItemQuery itemQuery){
        Page<SmqItem> pageItem = smqItemService.pageQueryItem(current, limit, itemQuery);
       try {
           long total = pageItem.getTotal();//总记录数
           List<SmqItem> records = pageItem.getRecords();//数据list集合
           return R.ok().data("total",total).data("rows",records);
       }catch (Exception e){
           return R.error();
       }
    }

    //    添加类目接口的方法
    @Transactional
    @ApiOperation(value = "添加类目")
    @PostMapping("addItem")
    public R addItem(@RequestBody SmqItem smqItem){
        boolean save = smqItemService.save(smqItem);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //    根据类目id进行查询
    @ApiOperation(value = "根据类目id进行查询")
    @GetMapping("getItem/{id}")
    public R getItem(@PathVariable String id){
        SmqItem smqItem = smqItemService.getById(id);
        return  R.ok().data("item",smqItem);
    }
    //    类目修改功能
    @Transactional
    @ApiOperation(value = "根据类目id进行修改")
    @PostMapping("updateItem")
    public R updateItem(@RequestBody SmqItem smqItem){
        boolean flag = smqItemService.updateById(smqItem);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

