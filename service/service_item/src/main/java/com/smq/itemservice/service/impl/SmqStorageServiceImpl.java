package com.smq.itemservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.smq.commonutils.JwtUtils;
import com.smq.itemservice.entity.*;
import com.smq.itemservice.entity.vo.LoginInfoVO;
import com.smq.itemservice.entity.vo.StorageQuery;
import com.smq.itemservice.entity.vo.StorageVO;
import com.smq.itemservice.mapper.SmqStorageMapper;
import com.smq.itemservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * 库存明细表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-07-18
 */
@Service
public class SmqStorageServiceImpl extends ServiceImpl<SmqStorageMapper, SmqStorage> implements SmqStorageService {
    @Resource
    private SmqStorageMapper smqStorageMapper;
    @Resource
    private SmqItemService smqItemService;
    @Resource
    private SmqMemberService smqMemberService;
    @Resource
    private SmqRecordService smqRecordService;

    @Resource
    private SmqFavorateService smqFavorateService;
    //多表联查返回StorageVO封装的集合
    //查询所有库存记录
    public List<StorageVO> getStorageVOlist(){
        MPJLambdaWrapper<SmqStorage> wrapper=new MPJLambdaWrapper<SmqStorage>()
                .selectAll(SmqStorage.class)//查询SmqStorage表的全部字段
                .select(SmqItem::getName,SmqItem::getCategory,SmqItem::getGrade,SmqItem::getBrand)
                .leftJoin(SmqItem.class,SmqItem::getId,SmqStorage::getItemId)
                .select(SmqLocation::getLocation,SmqLocation::getBasement)
                .leftJoin(SmqLocation.class,SmqLocation::getId,SmqStorage::getLocationId);
        List<StorageVO> list=smqStorageMapper.selectJoinList(StorageVO.class,wrapper);
        return list;
    }

    //多表联查返回StorageVO封装的集合
    //分页查询库存记录
    public IPage<StorageVO> getStorageVOpage(Page<StorageVO> page){
        MPJLambdaWrapper<SmqStorage> wrapper=new MPJLambdaWrapper<SmqStorage>()
                .selectAll(SmqStorage.class)//查询SmqStorage表的全部字段
                .select(SmqItem::getName,SmqItem::getCategory,SmqItem::getGrade,SmqItem::getBrand)
                .leftJoin(SmqItem.class,SmqItem::getId,SmqStorage::getItemId)
                .select(SmqLocation::getLocation,SmqLocation::getBasement)
                .leftJoin(SmqLocation.class,SmqLocation::getId,SmqStorage::getLocationId);
        IPage<StorageVO> pageStorage=smqStorageMapper.selectJoinPage(page,StorageVO.class,wrapper);
        return pageStorage;
    }
    //多表联查返回StorageVO封装的集合
    //分页带条件查询库存记录
    public IPage<StorageVO> getStorageVOpageCondition(Page<StorageVO> page, JoinWrappers joinWrappers){
        MPJLambdaWrapper<SmqStorage> wrapper=new MPJLambdaWrapper<SmqStorage>()
                .selectAll(SmqStorage.class)//查询SmqStorage表的全部字段
                .select(SmqItem::getName,SmqItem::getCategory,SmqItem::getGrade,SmqItem::getBrand)
                .leftJoin(SmqItem.class,SmqItem::getId,SmqStorage::getItemId)
                .select(SmqLocation::getLocation,SmqLocation::getBasement)
                .leftJoin(SmqLocation.class,SmqLocation::getId,SmqStorage::getLocationId);
        IPage<StorageVO> pageStorage=smqStorageMapper.selectJoinPage(page,StorageVO.class,wrapper);
        return pageStorage;
    }
//分页条件查询StorageVO对象的集合
    @Override
    public IPage<StorageVO> pageQueryStorage(long current, long limit, StorageQuery storageQuery) {
//        创建Page对象
        Page<StorageVO> page=new Page<>(current,limit);
//        构建查询条件
        MPJLambdaWrapper<SmqStorage> wrapper=new MPJLambdaWrapper<SmqStorage>()
                .selectAll(SmqStorage.class)//查询SmqStorage表的全部字段
                .select(SmqItem::getName,SmqItem::getCategory,SmqItem::getGrade,
                        SmqItem::getBrand,SmqItem::getSpecification, SmqItem::getUnit,
                        SmqItem::getPacking,SmqItem::getBatchNumber, SmqItem::getCasNumber,
                        SmqItem::getMatter,SmqItem::getAlertNumber, SmqItem::getAllowStorage)
                .innerJoin(SmqItem.class,SmqItem::getId,SmqStorage::getItemId)
                .select(SmqLocation::getLocation,SmqLocation::getBasement,SmqLocation::getIsUseful)
                .innerJoin(SmqLocation.class,SmqLocation::getId,SmqStorage::getLocationId);

//         拼接多条件组合查询
//        拼接查询SmqStorage表中字段的条件
        wrapper.gt(!StringUtils.isEmpty(storageQuery.getStorageNumber())&&storageQuery.getStorageNumber().intValue()>0,SmqStorage::getStorageNumber,storageQuery.getStorageNumber());
        wrapper.gt(!StringUtils.isEmpty(storageQuery.getTotalStorage())&&storageQuery.getTotalStorage().longValue()>0,SmqStorage::getTotalStorage,storageQuery.getTotalStorage());

        wrapper.gt(!StringUtils.isEmpty(storageQuery.getBegin()),SmqStorage::getGmtModified,storageQuery.getBegin());
        wrapper.le(!StringUtils.isEmpty(storageQuery.getEnd()),SmqStorage::getGmtModified,storageQuery.getEnd());
//        拼接查询SmqItem表中字段的条件
        wrapper.like(!StringUtils.isEmpty(storageQuery.getName()),SmqItem::getName,storageQuery.getName());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getCategory()),SmqItem::getCategory,storageQuery.getCategory());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getGrade()),SmqItem::getGrade,storageQuery.getGrade());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getBrand()),SmqItem::getBrand,storageQuery.getBrand());
        wrapper.eq(!StringUtils.isEmpty(storageQuery.getSpecification())&&storageQuery.getSpecification() >=0,SmqItem::getSpecification,storageQuery.getSpecification());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getUnit()),SmqItem::getUnit,storageQuery.getUnit());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getPacking()),SmqItem::getPacking,storageQuery.getPacking());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getBatchNumber()),SmqItem::getBatchNumber,storageQuery.getBatchNumber());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getCasNumber()),SmqItem::getCategory,storageQuery.getCasNumber());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getMatter()),SmqItem::getMatter,storageQuery.getMatter());
        wrapper.le(!StringUtils.isEmpty(storageQuery.getAlertNumber())&&storageQuery.getAlertNumber().intValue()>0,SmqItem::getAlertNumber,storageQuery.getAlertNumber());
        wrapper.gt(!StringUtils.isEmpty(storageQuery.getAllowStorage())&&storageQuery.getAllowStorage().intValue()>0,SmqItem::getAllowStorage,storageQuery.getAllowStorage());
        //        拼接查询SmqLocation表中字段的条件
        wrapper.like(!StringUtils.isEmpty(storageQuery.getLocation()),SmqLocation::getLocation,storageQuery.getLocation());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getBasement()),SmqLocation::getBasement,storageQuery.getBasement());
//        //         仅查询所有启用的位置库存明细
//        wrapper.eq(SmqLocation::getIsUseful,1);
//
//      排序
        wrapper.orderByDesc(SmqStorage::getGmtModified);
//        调用方法实现条件查询分页
        IPage<StorageVO> pageStorage=smqStorageMapper.selectJoinPage(page,StorageVO.class,wrapper);
        return pageStorage;
    }
//根据id查询StorageVO对象
    @Override
    public StorageVO getOneStorage(Long id) {
        long sid =id;
        MPJLambdaWrapper<SmqStorage> wrapper=new MPJLambdaWrapper<SmqStorage>()
                .selectAll(SmqStorage.class)//查询SmqStorage表的全部字段
                .select(SmqItem::getName,SmqItem::getGrade,
                        SmqItem::getBrand,SmqItem::getSpecification,
                        SmqItem::getUnit,SmqItem::getPacking,SmqItem::getBatchNumber,SmqItem::getCasNumber
                        ,SmqItem::getCategory,SmqItem::getMatter,SmqItem::getAlertNumber
                        ,SmqItem::getAllowStorage)
                .innerJoin(SmqItem.class,SmqItem::getId,SmqStorage::getItemId)
                .select(SmqLocation::getLocation,SmqLocation::getBasement,SmqLocation::getIsUseful)
                .innerJoin(SmqLocation.class,SmqLocation::getId,SmqStorage::getLocationId)
                .eq(sid>0,SmqStorage::getId,sid);
        StorageVO storageVO = smqStorageMapper.selectJoinOne(StorageVO.class, wrapper);
        return storageVO;
    }

//盘点功能根据StorageVO对象修改Storage和Item//需要梳理盘点功能的具体逻辑
    @Transactional
    @Override
    public boolean updateByStorageVO(StorageVO storageVO) {
        Long itemId = storageVO.getItemId();
        Long id = storageVO.getId();
        Long locationId = storageVO.getLocationId();
//        从前端传回的库存信息
        Integer storageNumber = storageVO.getStorageNumber();
        BigDecimal totalStorage = storageVO.getTotalStorage();
        SmqStorage smqStorage = this.getById(id);
//        接受数据库查询到的tmpstorageNumber和tmptotalStorage
        Integer tmpstorageNumber = smqStorage.getStorageNumber();
        BigDecimal tmptotalStorage = smqStorage.getTotalStorage();
        //            先从数据库查到Item原始值
        SmqItem smqItem = smqItemService.getById(itemId);
        Integer itemStorageNumber = smqItem.getStorageNumber();
        BigDecimal itemTotalStorage = smqItem.getTotalStorage();

        boolean flag1=false,flag2=false;
        if ((itemStorageNumber+storageNumber-tmpstorageNumber)>=0
                &&(itemTotalStorage.add(totalStorage.subtract(tmptotalStorage))).longValue()>=0){
            //      更新smqStorage相关信息
            smqStorage.setItemId(itemId);
            smqStorage.setStorageNumber(storageNumber);
            smqStorage.setTotalStorage(totalStorage);
            smqStorage.setLocationId(locationId);
            flag1 = this.updateById(smqStorage);
            smqItem.setStorageNumber(itemStorageNumber+storageNumber-tmpstorageNumber);
            smqItem.setTotalStorage(itemTotalStorage.add(totalStorage.subtract(tmptotalStorage)));
            //      更新smqItem相关信息
            flag2 = smqItemService.updateById(smqItem);
        }

        return flag1&&flag2;
    }
//添加库存条目
    @Transactional
    @Override
    public boolean addStorage(StorageVO  storageVO, HttpServletRequest request) {
        Long itemId = storageVO.getItemId();
        Long locationId = storageVO.getLocationId();
//        从前端传回的库存信息
        Integer storageNumber = storageVO.getStorageNumber();
        BigDecimal totalStorage = storageVO.getTotalStorage();
        //调用jwt工具类，获取头部信息，返回用户id
        String sid = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据id获得登录用户信息
        LoginInfoVO loginInfoVObyId = smqMemberService.getLoginInfoVObyId(Long.parseLong(sid));
        if (storageNumber>=0&&totalStorage.longValue()>=0){
            //            更新item
            //            先从数据库查到Item原始值
            SmqItem smqItem = smqItemService.getById(itemId);
            Integer itemStorageNumber = smqItem.getStorageNumber();
            BigDecimal itemTotalStorage = smqItem.getTotalStorage();
            smqItem.setStorageNumber(itemStorageNumber+storageNumber);
            smqItem.setTotalStorage(itemTotalStorage.add(totalStorage));
            boolean flag1 = smqItemService.updateById(smqItem);
            //            添加storage
            SmqStorage smqStorage = new SmqStorage();
            smqStorage.setItemId(itemId);
            smqStorage.setLocationId(locationId);
            smqStorage.setStorageNumber(storageNumber);
            smqStorage.setTotalStorage(totalStorage);
            boolean flag = this.save(smqStorage);
            //                  更新record表
            SmqRecord smqRecord=new SmqRecord();
            smqRecord.setMemberId(loginInfoVObyId.getId());
            smqRecord.setStorageId(smqStorage.getId());
            smqRecord.setStorageNumber(itemStorageNumber);
            smqRecord.setTotalStorage(itemTotalStorage);
            smqRecord.setOpreation("新增");
            return flag&&flag1&&smqRecordService.save(smqRecord);
        }
       return  false;
    }
//入库功能
    @Transactional
    @Override
    public boolean inStorage(List<StorageVO> liststorageVO, HttpServletRequest request) {
        boolean flag=true;
        if(liststorageVO.size()>0){
            for (StorageVO storageVO:liststorageVO) {
                Long itemId = storageVO.getItemId();
                Long id = storageVO.getId();
                SmqStorage smqStorage = this.getById(id);
                Integer storageNumber = smqStorage.getStorageNumber();
                BigDecimal totalStorage = smqStorage.getTotalStorage();
                Integer handlestorageNumber = storageVO.getStorageNumber();
                BigDecimal handletotalStorage = storageVO.getTotalStorage();
                //调用jwt工具类，获取头部信息，返回用户id
                String sid = JwtUtils.getMemberIdByJwtToken(request);
                //查询数据库根据id获得登录用户信息
                LoginInfoVO loginInfoVObyId = smqMemberService.getLoginInfoVObyId(Long.parseLong(sid));
//                更新item和storage表的相关字段
                //            先从数据库查到Item原始值
                SmqItem smqItem = smqItemService.getById(itemId);
                if (handlestorageNumber>=0 && handletotalStorage.longValue()>=0){
//                    更新storage表
                    smqStorage.setStorageNumber(storageNumber+handlestorageNumber);
//                    更新item表
                    Integer itemStorageNumber = smqItem.getStorageNumber();
                    smqItem.setStorageNumber(itemStorageNumber+handlestorageNumber);
                    //                    更新storage表
                    smqStorage.setTotalStorage(totalStorage.add(handletotalStorage));
                    //                    更新item表
                    BigDecimal itemTotalStorage = smqItem.getTotalStorage();
                    smqItem.setTotalStorage(itemTotalStorage.add(handletotalStorage));
                    //                  更新record表
                    SmqRecord smqRecord=new SmqRecord();
                    smqRecord.setMemberId(loginInfoVObyId.getId());
                    smqRecord.setStorageId(smqStorage.getId());
                    smqRecord.setStorageNumber(handlestorageNumber);
                    smqRecord.setTotalStorage(handletotalStorage);
                    smqRecord.setOpreation("入库");
                    flag=flag&&this.updateById(smqStorage)&&smqItemService.updateById(smqItem)&&smqRecordService.save(smqRecord);
                }else {
                    flag=false;
                }
            }
            return  flag;
        }
        return false;
    }
    //    出库功能
    @Override
    @Transactional
    public boolean outStorage(List<StorageVO> liststorageVO, HttpServletRequest request) {
        boolean flag = true;
        if(liststorageVO.size()>0){
            for (StorageVO storageVO:liststorageVO) {
                Long itemId = storageVO.getItemId();
                Long id = storageVO.getId();
                SmqStorage smqStorage = this.getById(id);
                Integer storageNumber = smqStorage.getStorageNumber();
                BigDecimal totalStorage = smqStorage.getTotalStorage();
                Integer handlestorageNumber = storageVO.getStorageNumber();
                BigDecimal handletotalStorage = storageVO.getTotalStorage();
                //调用jwt工具类，获取头部信息，返回用户id
                String sid = JwtUtils.getMemberIdByJwtToken(request);
                //查询数据库根据id获得登录用户信息
                LoginInfoVO loginInfoVObyId = smqMemberService.getLoginInfoVObyId(Long.parseLong(sid));
                //                更新item和storage表的相关字段
                //            先从数据库查到Item原始值
                SmqItem smqItem = smqItemService.getById(itemId);
                Integer itemStorageNumber = smqItem.getStorageNumber();
                BigDecimal itemTotalStorage = smqItem.getTotalStorage();
                if (handlestorageNumber>=0
                        &&handlestorageNumber<=storageNumber
                        &&handlestorageNumber<=smqItem.getStorageNumber()
                        &&(handletotalStorage.longValue()>=0)
                        &&(handletotalStorage.longValue()<=totalStorage.longValue())
                        &&(handletotalStorage.longValue()<=itemTotalStorage.longValue())){
                    //                更新storage表的相关字段
                    smqStorage.setStorageNumber(storageNumber-handlestorageNumber);
                    //                更新item表的相关字段
                    smqItem.setStorageNumber(itemStorageNumber-handlestorageNumber);
                    smqStorage.setTotalStorage(totalStorage.subtract(handletotalStorage));
                    smqItem.setTotalStorage(itemTotalStorage.subtract(handletotalStorage));
                    //                  更新record表
                    SmqRecord smqRecord=new SmqRecord();
                    smqRecord.setMemberId(loginInfoVObyId.getId());
                    smqRecord.setStorageId(smqStorage.getId());
                    smqRecord.setStorageNumber(handlestorageNumber);
                    smqRecord.setTotalStorage(handletotalStorage);
                    smqRecord.setOpreation("出库");
                    flag=flag&&this.updateById(smqStorage)&&smqItemService.updateById(smqItem)&&smqRecordService.save(smqRecord);
                }
                else {
                    flag=false;
                }
            }
            return flag;
        }
        return false;
    }
//    删除库存记录
    @Transactional
    @Override
    public boolean deleteByID(Long id, HttpServletRequest request) {
        //调用jwt工具类，获取头部信息，返回用户id
        String sid = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据id获得登录用户信息
        LoginInfoVO loginInfoVObyId = smqMemberService.getLoginInfoVObyId(Long.parseLong(sid));
        SmqStorage smqStorage=this.getById(id);
        //                  更新record表
        SmqRecord smqRecord=new SmqRecord();
        smqRecord.setStorageNumber(smqStorage.getStorageNumber());
        smqRecord.setTotalStorage(smqStorage.getTotalStorage());
        smqRecord.setMemberId(loginInfoVObyId.getId());
        smqRecord.setStorageId(id);
        smqRecord.setOpreation("删除");
        return smqRecordService.save(smqRecord)&& this.removeById(id) ;
    }

//    分页带条件查询被收藏的库存
    @Override
    public IPage<StorageVO> pageQueryFavorateStorage(long current, long limit, StorageQuery storageQuery, HttpServletRequest request) {
        //调用jwt工具类，获取头部信息，返回用户id
        String sid = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据id获得登录用户id信息
        long loginInfoID = Long.parseLong(sid);
        //        创建Page对象
        Page<StorageVO> page=new Page<>(current,limit);
//        构建查询条件
        MPJLambdaWrapper<SmqStorage> wrapper=new MPJLambdaWrapper<SmqStorage>()
                .selectAll(SmqStorage.class)//查询SmqStorage表的全部字段
                .select(SmqItem::getName,SmqItem::getCategory,SmqItem::getGrade,
                        SmqItem::getBrand,SmqItem::getSpecification, SmqItem::getUnit,
                        SmqItem::getPacking,SmqItem::getBatchNumber, SmqItem::getCasNumber,
                        SmqItem::getMatter,SmqItem::getAlertNumber, SmqItem::getAllowStorage)
                .innerJoin(SmqItem.class,SmqItem::getId,SmqStorage::getItemId)
                .select(SmqLocation::getLocation,SmqLocation::getBasement,SmqLocation::getIsUseful)
                .innerJoin(SmqLocation.class,SmqLocation::getId,SmqStorage::getLocationId)
                .select(SmqFavorate::getMemberId,SmqFavorate::getStorageId)
                .innerJoin(SmqFavorate.class,SmqFavorate::getStorageId,SmqStorage::getId);

//         拼接多条件组合查询
//        拼接查询SmqFavorate表中字段的条件
        wrapper.eq(!StringUtils.isEmpty(loginInfoID),SmqFavorate::getMemberId,loginInfoID);
//        拼接查询SmqStorage表中字段的条件
        wrapper.gt(!StringUtils.isEmpty(storageQuery.getStorageNumber())&&storageQuery.getStorageNumber().intValue()>0,SmqStorage::getStorageNumber,storageQuery.getStorageNumber());
        wrapper.gt(!StringUtils.isEmpty(storageQuery.getTotalStorage())&&storageQuery.getTotalStorage().longValue()>0,SmqStorage::getTotalStorage,storageQuery.getTotalStorage());

        wrapper.gt(!StringUtils.isEmpty(storageQuery.getBegin()),SmqStorage::getGmtModified,storageQuery.getBegin());
        wrapper.le(!StringUtils.isEmpty(storageQuery.getEnd()),SmqStorage::getGmtModified,storageQuery.getEnd());
//        拼接查询SmqItem表中字段的条件
        wrapper.like(!StringUtils.isEmpty(storageQuery.getName()),SmqItem::getName,storageQuery.getName());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getCategory()),SmqItem::getCategory,storageQuery.getCategory());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getGrade()),SmqItem::getGrade,storageQuery.getGrade());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getBrand()),SmqItem::getBrand,storageQuery.getBrand());
        wrapper.eq(!StringUtils.isEmpty(storageQuery.getSpecification())&&storageQuery.getSpecification() >=0,SmqItem::getSpecification,storageQuery.getSpecification());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getUnit()),SmqItem::getUnit,storageQuery.getUnit());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getPacking()),SmqItem::getPacking,storageQuery.getPacking());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getBatchNumber()),SmqItem::getBatchNumber,storageQuery.getBatchNumber());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getCasNumber()),SmqItem::getCategory,storageQuery.getCasNumber());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getMatter()),SmqItem::getMatter,storageQuery.getMatter());
        wrapper.le(!StringUtils.isEmpty(storageQuery.getAlertNumber())&&storageQuery.getAlertNumber().intValue()>0,SmqItem::getAlertNumber,storageQuery.getAlertNumber());
        wrapper.gt(!StringUtils.isEmpty(storageQuery.getAllowStorage())&&storageQuery.getAllowStorage().intValue()>0,SmqItem::getAllowStorage,storageQuery.getAllowStorage());
        //        拼接查询SmqLocation表中字段的条件
        wrapper.like(!StringUtils.isEmpty(storageQuery.getLocation()),SmqLocation::getLocation,storageQuery.getLocation());
        wrapper.like(!StringUtils.isEmpty(storageQuery.getBasement()),SmqLocation::getBasement,storageQuery.getBasement());
//        //         仅查询所有启用的位置库存明细
//        wrapper.eq(SmqLocation::getIsUseful,1);
//
//      排序
        wrapper.orderByDesc(SmqStorage::getGmtModified);
//        调用方法实现条件查询分页
        IPage<StorageVO> pageStorage=smqStorageMapper.selectJoinPage(page,StorageVO.class,wrapper);

        return pageStorage;
    }


}
