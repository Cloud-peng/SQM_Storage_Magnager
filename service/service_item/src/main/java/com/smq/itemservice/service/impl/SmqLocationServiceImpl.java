package com.smq.itemservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smq.itemservice.entity.SmqLocation;
import com.smq.itemservice.entity.vo.LocationQuery;
import com.smq.itemservice.mapper.SmqLocationMapper;
import com.smq.itemservice.service.SmqLocationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 位置表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-18
 */
@Service
public class SmqLocationServiceImpl extends ServiceImpl<SmqLocationMapper, SmqLocation> implements SmqLocationService {

//    分页条件查询位置信息
    @Override
    public Page<SmqLocation> pageQueryLocation(long current, long limit, LocationQuery locationQuery) {
        //        创建Page对象
        Page<SmqLocation> pageLocation=new Page<>(current,limit);
//        构建条件
        QueryWrapper<SmqLocation> wrapper=new QueryWrapper<>();
//         多条件组合查询
        String location = locationQuery.getLocation();
        String basement = locationQuery.getBasement();
        Integer isUseful = locationQuery.getIsUseful();
        String begin = locationQuery.getBegin();
        String end = locationQuery.getEnd();
        //        判断条件是否为空,如果不为空拼接条件
        if (!StringUtils.isEmpty(location)){
            wrapper.like("location",location);
        }
        if (!StringUtils.isEmpty(basement)){
            wrapper.like("basement",basement);
        }
        if (!StringUtils.isEmpty(isUseful)){
            wrapper.like("is_useful",isUseful);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.gt("gmt_modified",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
//      排序
        wrapper.orderByDesc("gmt_modified");
//        调用方法实现条件查询分页
        return this.page(pageLocation,wrapper);
    }
}
