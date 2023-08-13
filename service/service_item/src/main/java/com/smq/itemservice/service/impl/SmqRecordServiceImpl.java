package com.smq.itemservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.smq.itemservice.entity.*;
import com.smq.itemservice.entity.vo.RecordQuery;
import com.smq.itemservice.entity.vo.RecordVO;
import com.smq.itemservice.entity.vo.StorageQuery;
import com.smq.itemservice.entity.vo.StorageVO;
import com.smq.itemservice.mapper.SmqRecordMapper;
import com.smq.itemservice.service.SmqRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 人员表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-08-09
 */
@Service
public class SmqRecordServiceImpl extends ServiceImpl<SmqRecordMapper, SmqRecord> implements SmqRecordService {
    //多表联查返回RecordVO封装的集合
    //分页带条件查询领用记录
    @Resource
    private SmqRecordMapper smqRecordMapper;
    @Override
    public IPage<RecordVO> pageQueryRecords(long current, long limit, RecordQuery recordQuery) {
//        创建Page对象
        Page<RecordVO> page=new Page<>(current,limit);
//        构建查询条件
        MPJLambdaWrapper<SmqRecord> wrapper=new MPJLambdaWrapper<SmqRecord>()
                .selectAll(SmqRecord.class)//查询SmqRecord表的全部字段
                .select(SmqMember::getId,SmqMember::getEmployNumber,SmqMember::getEmployName)
                .innerJoin(SmqMember.class,SmqMember::getId,SmqRecord::getMemberId)
                .select(SmqStorage::getId,SmqStorage::getItemId,SmqStorage::getLocationId)
                .innerJoin(SmqStorage.class,SmqStorage::getId,SmqRecord::getStorageId)
                .select(SmqItem::getName,SmqItem::getCategory,SmqItem::getGrade,
                        SmqItem::getBrand,SmqItem::getSpecification, SmqItem::getUnit,
                        SmqItem::getPacking,SmqItem::getBatchNumber, SmqItem::getCasNumber,
                        SmqItem::getMatter)
                .innerJoin(SmqItem.class,SmqItem::getId,SmqStorage::getItemId)
                .select(SmqLocation::getLocation,SmqLocation::getBasement,SmqLocation::getIsUseful)
                .innerJoin(SmqLocation.class,SmqLocation::getId,SmqStorage::getLocationId)
                .disableSubLogicDel();//关闭副表逻辑删除

//         拼接多条件组合查询
//        拼接查询SmqRecord表中字段的条件
        wrapper.gt(!StringUtils.isEmpty(recordQuery.getStorageNumber()),SmqRecord::getStorageNumber,recordQuery.getStorageNumber());
        wrapper.gt(!StringUtils.isEmpty(recordQuery.getTotalStorage()),SmqRecord::getTotalStorage,recordQuery.getTotalStorage());
        wrapper.eq(!StringUtils.isEmpty(recordQuery.getOpreation()),SmqRecord::getOpreation,recordQuery.getOpreation());
        wrapper.gt(!StringUtils.isEmpty(recordQuery.getBegin()),SmqRecord::getGmtModified,recordQuery.getBegin());
        wrapper.le(!StringUtils.isEmpty(recordQuery.getEnd()),SmqRecord::getGmtModified,recordQuery.getEnd());

//        拼接查询SmqItem表中字段的条件
        wrapper.like(!StringUtils.isEmpty(recordQuery.getName()),SmqItem::getName,recordQuery.getName());
        wrapper.like(!StringUtils.isEmpty(recordQuery.getCategory()),SmqItem::getCategory,recordQuery.getCategory());
        wrapper.like(!StringUtils.isEmpty(recordQuery.getGrade()),SmqItem::getGrade,recordQuery.getGrade());
        wrapper.like(!StringUtils.isEmpty(recordQuery.getBrand()),SmqItem::getBrand,recordQuery.getBrand());
        wrapper.like(!StringUtils.isEmpty(recordQuery.getBatchNumber()),SmqItem::getBatchNumber,recordQuery.getBatchNumber());

        //        拼接查询SmqLocation表中字段的条件
        wrapper.like(!StringUtils.isEmpty(recordQuery.getLocation()),SmqLocation::getLocation,recordQuery.getLocation());
        wrapper.like(!StringUtils.isEmpty(recordQuery.getBasement()),SmqLocation::getBasement,recordQuery.getBasement());

        //        拼接查询SmqMember表中字段的条件
        wrapper.like(!StringUtils.isEmpty(recordQuery.getEmployName()),SmqMember::getEmployName,recordQuery.getEmployName());
        wrapper.like(!StringUtils.isEmpty(recordQuery.getEmployNumber()),SmqMember::getEmployNumber,recordQuery.getEmployNumber());
//        //         仅查询所有启用的位置库存明细
//        wrapper.eq(SmqLocation::getIsUseful,1);
//
//      排序
        wrapper.orderByDesc(SmqRecord::getGmtModified);
//        调用方法实现条件查询分页
        IPage<RecordVO> pageRecordVO=smqRecordMapper.selectJoinPage(page,RecordVO.class,wrapper);
        return pageRecordVO;

    }
//    添加使用记录
    @Transactional
    @Override
    public boolean addRecords(List<SmqRecord> listRecord) {
        int i=1;
        if(listRecord.size()>0){
            for (SmqRecord smqRecord:listRecord) {
                i = i*smqRecordMapper.insert(smqRecord);
            }
            return i>=1? true: false;
        }
        return  false;
    }


}
