package com.smq.eduservice.controller;


import com.smq.commonutils.R;
import com.smq.eduservice.entity.SmqItem;
import com.smq.eduservice.service.SmqItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-07-17
 */
@RestController
@RequestMapping("/eduservice/smq-item")
public class SmqItemController {
    @Autowired
    SmqItemService smqItemService;
    @GetMapping("getAllItem")
    public R getAllItem(){
        List<SmqItem> list = smqItemService.list(null);
        return R.ok().data("total",list.size()).data("item",list);
    }
}

