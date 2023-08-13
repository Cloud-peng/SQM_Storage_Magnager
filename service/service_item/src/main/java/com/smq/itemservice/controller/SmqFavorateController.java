package com.smq.itemservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smq.commonutils.R;
import com.smq.itemservice.entity.SmqFavorate;
import com.smq.itemservice.entity.SmqItem;
import com.smq.itemservice.entity.vo.StorageQuery;
import com.smq.itemservice.entity.vo.StorageVO;
import com.smq.itemservice.service.SmqFavorateService;
import com.smq.itemservice.service.SmqStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 收藏表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-08-10
 */
@Api(description = "库存收藏管理")
@RestController
@CrossOrigin
@RequestMapping("/smq/myfavorate/storageservice")
public class SmqFavorateController {
    @Resource
    SmqStorageService smqStorageService;
    @Resource
    SmqFavorateService smqFavorateService;
    //    我的收藏，条件查询分页的方法
    @ApiOperation(value = "我的收藏，条件查询分页库存列表")
    @PostMapping("pageStorageCondition/{current}/{limit}")
    public R pageStorageCondition(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) StorageQuery storageQuery, HttpServletRequest request){
        IPage<StorageVO> pageStorage=smqStorageService.pageQueryFavorateStorage(current,limit,storageQuery,request);
        try {
            long total = pageStorage.getTotal();//总记录数
            List<StorageVO> records = pageStorage.getRecords();//数据list集合
            return R.ok().data("total",total).data("rows",records);
        }catch (Exception e){
            return R.error();
        }
    }
    //    添加收藏的方法
    @ApiOperation(value = "添加库存收藏")
    @PostMapping("addFavorate")
    public R addFavorate( @RequestBody StorageVO storageVO, HttpServletRequest request){
        if(storageVO.getId()>=0){
            R r = smqFavorateService.addFavorateBySto(storageVO, request);
            if (r.getSuccess()) {
                return R.ok().message("收藏成功");
            }
            else {
                return R.error().message(r.getMessage());
            }

        }
        return  R.error().message("收藏失败");
    }

    //    取消收藏的方法
    @ApiOperation(value = "取消库存收藏")
    @PostMapping("deleteFavorate")
    public R deleteFavorate( @RequestBody StorageVO storageVO,HttpServletRequest request){
        if(storageVO.getId()>=0){
            R r = smqFavorateService.deleteFavorate(storageVO, request);
            if (r.getSuccess()) {
                return R.ok().message("取消收藏成功");
            }
        }
        return  R.error().message("取消收藏失败");
    }

}

