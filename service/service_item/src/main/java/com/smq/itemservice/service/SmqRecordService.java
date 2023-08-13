package com.smq.itemservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smq.itemservice.entity.SmqRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smq.itemservice.entity.vo.RecordQuery;
import com.smq.itemservice.entity.vo.RecordVO;
import com.smq.itemservice.entity.vo.StorageQuery;

import java.util.List;

/**
 * <p>
 * 人员表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-08-09
 */
public interface SmqRecordService extends IService<SmqRecord> {

    IPage<RecordVO> pageQueryRecords(long current, long limit, RecordQuery recordQuery);

    boolean addRecords(List<SmqRecord> listRecord);
}
