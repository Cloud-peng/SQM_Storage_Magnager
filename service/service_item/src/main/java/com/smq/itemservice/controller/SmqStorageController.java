package com.smq.itemservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.smq.commonutils.JwtUtils;
import com.smq.commonutils.R;
import com.smq.itemservice.entity.SmqItem;
import com.smq.itemservice.entity.SmqLocation;
import com.smq.itemservice.entity.SmqMember;
import com.smq.itemservice.entity.SmqStorage;
import com.smq.itemservice.entity.vo.ItemQuery;
import com.smq.itemservice.entity.vo.LoginInfoVO;
import com.smq.itemservice.entity.vo.StorageQuery;
import com.smq.itemservice.entity.vo.StorageVO;
import com.smq.itemservice.mapper.SmqStorageMapper;
import com.smq.itemservice.service.SmqItemService;
import com.smq.itemservice.service.SmqMemberService;
import com.smq.itemservice.service.SmqStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * 库存明细表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-07-18
 */
@Api(description = "库存管理")
@RestController
@RequestMapping("/smq/storageservice")
@CrossOrigin
public class SmqStorageController {
    @Autowired
    SmqStorageService smqStorageService;
    @Resource
    SmqMemberService smqMemberService;
    @ApiOperation(value = "所有库存列表")
    @GetMapping("findAllstorage")
    public R getAllStorage() {
        List<StorageVO> list = smqStorageService.getStorageVOlist();
        return R.ok().data("total", list.size()).data("item", list);
    }


    @Transactional
    //   逻辑删除库存
    @ApiOperation(value = "逻辑删除库存")
    @DeleteMapping("storage/{id}")
    public R removeStorage(@PathVariable Long id, HttpServletRequest request) {
        boolean flag = smqStorageService.deleteByID(id,request);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //    分页查询库存的方法
    //current当前页
    //limit每页显示记录数
    @ApiOperation(value = "分页库存列表")
    @GetMapping("pageStorage/{current}/{limit}")
    public R pageListStorage(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit) {
            return  this.pageStorageCondition(current,limit, new StorageQuery());
    }

    //    条件查询分页的方法
    @ApiOperation(value = "条件查询分页库存列表")
    @PostMapping ("pageStorageCondition/{current}/{limit}")
    public R pageStorageCondition(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) StorageQuery storageQuery){
        IPage<StorageVO> pageStorage=smqStorageService.pageQueryStorage(current,limit,storageQuery);
        try {
            long total = pageStorage.getTotal();//总记录数
            List<StorageVO> records = pageStorage.getRecords();//数据list集合
            return R.ok().data("total",total).data("rows",records);
        }catch (Exception e){
            return R.error();
        }
    }


    //    根据库存条目id进行查询返回StorageVO封装的数据
    @ApiOperation(value = "库存条目id进行查询")
    @GetMapping("getStorage/{id}")
    public R getStorage(@PathVariable Long id){
        StorageVO storageVO=smqStorageService.getOneStorage(id);
        return  R.ok().data("storageVO",storageVO);
    }
    //    库存条目(盘点)功能//******待确认，盘点功能******
    @ApiOperation(value = "根据库存条目id进行修改")
    @PostMapping("updateStorage")
    public R updateStorage(@RequestBody StorageVO storageVO){
        boolean flag = smqStorageService.updateByStorageVO(storageVO);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //    库存条目添加功能
    @ApiOperation(value = "添加库存条目")
    @PostMapping("addStorage")
    public R addStorage(@RequestBody StorageVO storageVO, HttpServletRequest request){
       boolean flag=smqStorageService.addStorage(storageVO,request);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //    入库功能
    @Transactional
    @ApiOperation(value = "根据操作条目的列表进行修改")
    @PostMapping("inStorage")
    public R inStorage(@RequestBody List<StorageVO> liststorageVO, HttpServletRequest request){
        boolean flag=smqStorageService.inStorage(liststorageVO,request);

        if (flag){
            return R.ok();
        }else {
            return R.error().message("入库失败");
        }
    }
    //    出库功能
    @Transactional
    @ApiOperation(value = "根据操作条目的列表进行修改")
    @PostMapping("outStorage")
    public R outStorage(@RequestBody List<StorageVO> liststorageVO, HttpServletRequest request){
        boolean flag=smqStorageService.outStorage(liststorageVO,request);
        if (flag){
            return R.ok();
        }else {
            return R.error().message("出库失败");
        }
    }
}

