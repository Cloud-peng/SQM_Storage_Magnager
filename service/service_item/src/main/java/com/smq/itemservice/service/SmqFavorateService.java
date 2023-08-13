package com.smq.itemservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smq.commonutils.R;
import com.smq.itemservice.entity.SmqFavorate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smq.itemservice.entity.vo.StorageQuery;
import com.smq.itemservice.entity.vo.StorageVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 收藏表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-08-10
 */
public interface SmqFavorateService extends IService<SmqFavorate> {

    IPage<StorageVO> pageQueryFavorateStorage(long current, long limit, StorageQuery storageQuery, HttpServletRequest request);

    R addFavorateBySto(StorageVO storageVO, HttpServletRequest request);

    R deleteFavorate(StorageVO storageVO, HttpServletRequest request);
}
