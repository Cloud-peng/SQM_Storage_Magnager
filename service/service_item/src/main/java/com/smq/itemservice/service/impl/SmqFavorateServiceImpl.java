package com.smq.itemservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smq.commonutils.JwtUtils;
import com.smq.commonutils.R;
import com.smq.itemservice.entity.SmqFavorate;
import com.smq.itemservice.entity.SmqStorage;
import com.smq.itemservice.entity.vo.LoginInfoVO;
import com.smq.itemservice.entity.vo.StorageQuery;
import com.smq.itemservice.entity.vo.StorageVO;
import com.smq.itemservice.mapper.SmqFavorateMapper;
import com.smq.itemservice.service.SmqFavorateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smq.itemservice.service.SmqMemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 收藏表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-08-10
 */
@Service
public class SmqFavorateServiceImpl extends ServiceImpl<SmqFavorateMapper, SmqFavorate> implements SmqFavorateService {
    @Resource
    private SmqMemberService smqMemberService;
    @Resource
    private SmqFavorateService smqFavorateService;
    @Override
    public IPage<StorageVO> pageQueryFavorateStorage(long current, long limit, StorageQuery storageQuery, HttpServletRequest request) {

        return null;
    }
//    添加收藏
    @Transactional
    @Override
    public R addFavorateBySto(StorageVO storageVO, HttpServletRequest request) {
        //调用jwt工具类，获取头部信息，返回用户id
        String sid = JwtUtils.getMemberIdByJwtToken(request);
        long logininfoid = Long.parseLong(sid);
        SmqFavorate smqFavorate=new SmqFavorate();
        smqFavorate.setMemberId(logininfoid);
        smqFavorate.setStorageId(storageVO.getId());
        Map<String, Object> map=new HashMap<>();
        map.put("member_id",logininfoid);
        map.put("storage_id",storageVO.getId());
        SmqFavorate one = this.getOne(new QueryWrapper<SmqFavorate>().allEq(map));
        if (one != null) {
            return R.error().message("请勿重复收藏");
        }
        else {
            if (smqFavorateService.saveOrUpdate(smqFavorate)){
                return R.ok().message("收藏成功");
            }
            return R.error().message("收藏失败");
        }
    }
//    取消收藏
    @Override
    public R deleteFavorate(StorageVO storageVO, HttpServletRequest request) {
        //调用jwt工具类，获取头部信息，返回用户id
        String sid = JwtUtils.getMemberIdByJwtToken(request);
        long logininfoid = Long.parseLong(sid);
        Map<String, Object> map=new HashMap<>();
        map.put("member_id",logininfoid);
        map.put("storage_id",storageVO.getId());
        boolean flag = smqFavorateService.removeByMap(map);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }
}
