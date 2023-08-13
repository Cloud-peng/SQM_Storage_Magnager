package com.smq.itemservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smq.itemservice.entity.SmqItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smq.itemservice.entity.vo.ItemQuery;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-16
 */
public interface SmqItemService extends IService<SmqItem> {

    Page<SmqItem> pageQueryItem(long current, long limit, ItemQuery itemQuery);
}
