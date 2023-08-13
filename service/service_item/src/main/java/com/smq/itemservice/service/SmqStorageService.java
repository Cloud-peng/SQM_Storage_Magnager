package com.smq.itemservice.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.toolkit.JoinWrappers;
import com.smq.itemservice.entity.SmqItem;
import com.smq.itemservice.entity.SmqStorage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smq.itemservice.entity.vo.StorageQuery;
import com.smq.itemservice.entity.vo.StorageVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 库存明细表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-18
 */
public interface SmqStorageService extends IService<SmqStorage> {

    List<StorageVO> getStorageVOlist();
    IPage<StorageVO> getStorageVOpage(Page<StorageVO> page);

    IPage<StorageVO> getStorageVOpageCondition(Page<StorageVO> page, JoinWrappers joinWrappers);

    IPage<StorageVO> pageQueryStorage(long current, long limit, StorageQuery storageQuery);

    StorageVO getOneStorage(Long id);

    boolean updateByStorageVO(StorageVO storageVO);

    boolean addStorage(StorageVO storageVO, HttpServletRequest request);

    boolean inStorage(List<StorageVO> liststorageVO, HttpServletRequest request);

    boolean outStorage(List<StorageVO> liststorageVO, HttpServletRequest request);

    boolean deleteByID(Long id, HttpServletRequest request);

    IPage<StorageVO> pageQueryFavorateStorage(long current, long limit, StorageQuery storageQuery, HttpServletRequest request);
}
