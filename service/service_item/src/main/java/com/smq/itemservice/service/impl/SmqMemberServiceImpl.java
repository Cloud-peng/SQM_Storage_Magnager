package com.smq.itemservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smq.commonutils.JwtUtils;
import com.smq.commonutils.MD5;
import com.smq.commonutils.R;
import com.smq.itemservice.entity.SmqMember;
import com.smq.itemservice.entity.vo.LoginInfoVO;
import com.smq.itemservice.entity.vo.UpdatePasswordVO;
import com.smq.itemservice.mapper.SmqMemberMapper;
import com.smq.itemservice.service.SmqMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smq.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 人员表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-08-06
 */
@Service
public class SmqMemberServiceImpl extends ServiceImpl<SmqMemberMapper, SmqMember> implements SmqMemberService {
    @Resource
    SmqMemberService smqMemberService;
    //登录的方法
    @Override
    public R login(SmqMember smqMember) {
        //工号和密码做登录
        //1、获取工号和密码
        String employNumber = smqMember.getEmployNumber();
        String password = smqMember.getPassword();
        //2、如过工号或密码为空，直接返回登录失败
        if (StringUtils.isEmpty(employNumber)||StringUtils.isEmpty(password)){
//            throw new GuliException(20001,"工号或密码为空");
            return R.error().message("工号或密码为空");
        }
        //判断工号是否正确
        QueryWrapper<SmqMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("employ_number",employNumber);
        SmqMember employNumberMember = baseMapper.selectOne(queryWrapper);
        if (employNumberMember == null){
//            throw new GuliException(20001,"工号不存在");
            return R.error().message("工号不存在");
        }
        //判断密码是否相等
        //数据库密码进行加密，不能直接对比
        //MD5对密码进行加密，再与数据库进行比较
        String password1 = employNumberMember.getPassword();
        if (!MD5.encrypt(password).equals(password1)){
//            throw new GuliException(20001,"密码错误");
            return R.error().message("密码错误");
        }

        //判断用户是否被禁用 1（true）可用，0（false）禁用
        if(employNumberMember.getIsUseful()==0){
            throw new GuliException(20001,"用户被禁用登录失败");
        }
        //登录成功
        //按照jwt生产token返回
        String token = JwtUtils.getJwtToken(employNumberMember.getId(),employNumberMember.getEmployNumber(), employNumberMember.getEmployName());
        return R.ok().data("token",token);
    }
//    修改密码
    @Transactional
    public R UpdatePassword(UpdatePasswordVO updatePasswordVO, HttpServletRequest request){
        //调用jwt工具类，获取头部信息，返回用户id
        String sid = JwtUtils.getMemberIdByJwtToken(request);
        long logininfoid = Long.parseLong(sid);
        SmqMember smqMember = smqMemberService.getById(logininfoid);
        if (smqMember == null) {
            return R.error().message("用户不存在");
        }
        String oldPassword = updatePasswordVO.getOldPassword();
        String newPassword = updatePasswordVO.getNewPassword();
        String checkedNewPassword = updatePasswordVO.getCheckedNewPassword();
//        判断密码是否为空
        if (oldPassword==null||oldPassword.isEmpty()||
            newPassword==null||newPassword.isEmpty()||!newPassword.equals(checkedNewPassword)||newPassword.length()<6||
                checkedNewPassword==null||!checkedNewPassword.equals(newPassword)||checkedNewPassword.length()<6||checkedNewPassword.isEmpty()
        ){
            return R.error().message("修改失败");
        }if (oldPassword.equals(newPassword)) {
            return R.error().message("新密码不得与原密码一致");
        }
        else {
            //判断密码是否相等
            //数据库密码进行加密，不能直接对比
            //MD5对密码进行加密，再与数据库进行比较
            String password1 = smqMember.getPassword();
            if (!MD5.encrypt(oldPassword).equals(password1)){
//                throw new GuliException(20001,"原密码错误,修改失败");
                return R.error().message("原密码错误,修改失败");
            }else {
                smqMember.setPassword(MD5.encrypt(checkedNewPassword));
                if (smqMemberService.updateById(smqMember)){
                    return R.ok().message("修改成功，请退出后重选登录");
                }else {
                    return R.error().message("修改失败,请稍后重试");
                }

            }
        }
    }

    @Override
    public LoginInfoVO getLoginInfoVObyId(long id) {
        SmqMember member = this.getById(id);
        LoginInfoVO loginInfoVO=new LoginInfoVO();
        BeanUtils.copyProperties(member,loginInfoVO);
        return  loginInfoVO;
    }
}
