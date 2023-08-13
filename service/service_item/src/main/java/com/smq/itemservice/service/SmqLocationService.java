package com.smq.itemservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smq.itemservice.entity.SmqLocation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smq.itemservice.entity.vo.LocationQuery;

/**
 * <p>
 * 位置表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-18
 */
public interface SmqLocationService extends IService<SmqLocation> {

    Page<SmqLocation> pageQueryLocation(long current, long limit, LocationQuery locationQuery);
}
