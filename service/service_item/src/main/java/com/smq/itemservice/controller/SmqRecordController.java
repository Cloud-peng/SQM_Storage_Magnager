package com.smq.itemservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smq.commonutils.R;
import com.smq.itemservice.entity.SmqRecord;
import com.smq.itemservice.entity.vo.RecordQuery;
import com.smq.itemservice.entity.vo.RecordVO;
import com.smq.itemservice.entity.vo.StorageQuery;
import com.smq.itemservice.entity.vo.StorageVO;
import com.smq.itemservice.service.SmqRecordService;
import com.smq.itemservice.service.SmqStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 人员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-08-09
 */
@Api(description = "使用记录")
@RestController
@RequestMapping("/smq/recordservice")
@CrossOrigin
public class SmqRecordController {
    @Autowired
    SmqRecordService smqRecordService;
    //    条件查询分页使用记录的方法
    @ApiOperation(value = "条件查询分页使用记录列表")
    @PostMapping("pageRecordCondition/{current}/{limit}")
    public R pageRecordCondition(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) RecordQuery recordQuery){
        IPage<RecordVO> pageStorage=smqRecordService.pageQueryRecords(current,limit,recordQuery);
        try {
            long total = pageStorage.getTotal();//总记录数
            List<RecordVO> records = pageStorage.getRecords();//数据list集合
            return R.ok().data("total",total).data("rows",records);
        }catch (Exception e){
            return R.error();
        }
    }

    //    添加使用记录的方法
    @ApiOperation(value = "添加使用记录的方法")
    @PostMapping("addRecord")
    public R addRecord(
            @RequestBody List<SmqRecord> listRecords){
        boolean flag=smqRecordService.addRecords(listRecords);
        if (flag){
            return R.ok();
        }else {
            return R.error().message("入库失败");
        }
    }

}

