package com.smq.itemservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smq.commonutils.R;
import com.smq.itemservice.entity.SmqItem;
import com.smq.itemservice.entity.SmqLocation;
import com.smq.itemservice.entity.vo.ItemQuery;
import com.smq.itemservice.entity.vo.LocationQuery;
import com.smq.itemservice.service.SmqItemService;
import com.smq.itemservice.service.SmqLocationService;
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
 * 位置表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-07-18
 */
@Api(description = "位置管理")
@RestController
@RequestMapping("/smq/locationservice")
@CrossOrigin
public class SmqLocationController {
    @Autowired
    SmqLocationService smqLocationService;
    @ApiOperation(value = "所有位置列表")
    @GetMapping("findAllLocation")
    public R getAllLocations() {
        List<SmqLocation> list = smqLocationService.list(null);
//            smqItemService.list();
        return R.ok().data("total", list.size()).data("location", list);
    }
    @Transactional
    //   逻辑删除位置
    @ApiOperation(value = "逻辑删除位置")
    @DeleteMapping("location/{id}")
    public R removeLocation(@PathVariable Long id) {
        boolean flag = smqLocationService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //    分页查询位置的方法
    //current当前页
    //limit每页显示记录数
    @ApiOperation(value = "分页位置列表")
    @GetMapping("pageListLocation/{current}/{limit}")
    public R pageListLocation(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit) {
//        创建page对象
        Page<SmqLocation> pageLocation = new Page<>(current, limit);
//        调用方法实现分页
//        调用方法的时候，底层实现封装，把分页所有数据封装到pageItem对象里面
        smqLocationService.page(pageLocation,null);

        long total = pageLocation.getTotal();//总记录数
        List<SmqLocation> records = pageLocation.getRecords();//数据list集合

        return R.ok().data("total",total).data("rows",records);
    }
    //    条件查询分页的方法
    @ApiOperation(value = "条件查询分页位置列表")
    @PostMapping ("pageLocationCondition/{current}/{limit}")
    public R pageLocationCondition(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) LocationQuery locationQuery){
        Page<SmqLocation> pageLocation=smqLocationService.pageQueryLocation(current,limit,locationQuery);
        try {
            long total = pageLocation.getTotal();//总记录数
            List<SmqLocation> records = pageLocation.getRecords();//数据list集合
            return R.ok().data("total",total).data("rows",records);
        }catch (Exception e){
            return R.error();
        }
    }

    //    添加位置的方法
    @Transactional
    @ApiOperation(value = "添加位置")
    @PostMapping("addLocation")
    public R addLocation(@RequestBody SmqLocation smqLocation){
        boolean save = smqLocationService.save(smqLocation);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //    根据位置id进行查询
    @ApiOperation(value = "根据类目id进行查询")
    @GetMapping("getLocation/{id}")
    public R getLocation(@PathVariable String id){
        SmqLocation smqLocation = smqLocationService.getById(id);
        return  R.ok().data("item",smqLocation);
    }
    //    位置修改功能
    @Transactional
    @ApiOperation(value = "根据位置id进行修改")
    @PostMapping("updateLocation")
    public R updateLocation(@RequestBody SmqLocation smqLocation){
        boolean flag = smqLocationService.updateById(smqLocation);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

