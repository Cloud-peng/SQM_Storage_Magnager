package com.smq.itemservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smq.itemservice.entity.SmqItem;
import com.smq.itemservice.entity.vo.ItemQuery;
import com.smq.itemservice.mapper.SmqItemMapper;
import com.smq.itemservice.service.SmqItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-16
 */
@Service
public class SmqItemServiceImpl extends ServiceImpl<SmqItemMapper, SmqItem> implements SmqItemService {
    @Override
    public Page<SmqItem> pageQueryItem(long current, long limit, ItemQuery itemQuery) {
        //        创建Page对象
        Page<SmqItem> pageItem=new Page<>(current,limit);
//        构建条件
        QueryWrapper<SmqItem> wrapper=new QueryWrapper<>();

        String name = itemQuery.getName();
        String category = itemQuery.getCategory();
        String grade = itemQuery.getGrade();
        String brand = itemQuery.getBrand();
        Integer specification = itemQuery.getSpecification();
        String unit = itemQuery.getUnit();
        String packing = itemQuery.getPacking();
        String batchNumber = itemQuery.getBatchNumber();
        String casNumber = itemQuery.getCasNumber();
        String matter = itemQuery.getMatter();
        Integer storageNumber = itemQuery.getStorageNumber();
        BigDecimal totalStorage = itemQuery.getTotalStorage();
        Integer alertNumber = itemQuery.getAlertNumber();
        Integer allowStorage = itemQuery.getAllowStorage();
        String begin = itemQuery.getBegin();
        String end = itemQuery.getEnd();
        //        判断条件是否为空,如果不为空拼接条件
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(category)){
            wrapper.like("category",category);
        }
        if (!StringUtils.isEmpty(grade)){
            wrapper.like("grade",grade);
        }
        if (!StringUtils.isEmpty(brand)){
            wrapper.like("brand",brand);
        }
        if (!StringUtils.isEmpty(specification)&&specification >=0){
            wrapper.eq("specification",specification);
        }
        if (!StringUtils.isEmpty(unit)){
            wrapper.like("unit",unit);
        }
        if (!StringUtils.isEmpty(packing)){
            wrapper.like("packing",packing);
        }
        if (!StringUtils.isEmpty(batchNumber)){
            wrapper.like("batch_number",batchNumber);
        }
        if (!StringUtils.isEmpty(casNumber)){
            wrapper.like("cas_number",casNumber);
        }
        if (!StringUtils.isEmpty(matter)){
            wrapper.like("matter",matter);
        }
        if (!StringUtils.isEmpty(storageNumber)&&storageNumber.intValue()>0){
            wrapper.le("storage_number",storageNumber);
        }
        if (!StringUtils.isEmpty(totalStorage)&&totalStorage.longValue()>0){
            wrapper.le("total_storage",totalStorage);
        }
        if (!StringUtils.isEmpty(alertNumber)&&alertNumber.intValue()>0){
            wrapper.le("alert_number",alertNumber);
        }
        if (!StringUtils.isEmpty(allowStorage)&&allowStorage.intValue()>0){
            wrapper.gt("allow_storage",allowStorage);
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
        return this.page(pageItem,wrapper);
    }
}
