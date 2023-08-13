package com.smq.itemservice.controller;


import com.smq.commonutils.JwtUtils;
import com.smq.commonutils.R;
import com.smq.itemservice.entity.SmqMember;
import com.smq.itemservice.entity.vo.LoginInfoVO;
import com.smq.itemservice.entity.vo.UpdatePasswordVO;
import com.smq.itemservice.service.SmqMemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 人员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-08-06
 */
@Api(description = "用户管理")
@RestController
@RequestMapping("/smq/memberservice")
@CrossOrigin
public class SmqMemberController {
    @Autowired
    private SmqMemberService smqMemberService;
    //工号和密码登录
    @PostMapping("login")
    public R login(@RequestBody SmqMember smqMember){
//        String token =smqMemberService.login(smqMember);
//        return R.ok().data("token",token);
        return smqMemberService.login(smqMember);
    }
//
//    //电话、密码、验证码、昵称进行注册
//    @PostMapping("register")
//    public R register(@RequestBody RegisterVo registerVo){
//        ucenterMemberService.register(registerVo);
//        return R.ok();
//    }
    //根据用户id获取用户信息
    @GetMapping("getLoginInfo")
    public R getMemberInfo(HttpServletRequest request){
        if (JwtUtils.checkToken(request)) {
            //调用jwt工具类，获取头部信息，返回用户id
            String sid = JwtUtils.getMemberIdByJwtToken(request);
            //查询数据库根据id获得登录用户信息
            LoginInfoVO loginInfoVObyId = smqMemberService.getLoginInfoVObyId(Long.parseLong(sid));
            return R.ok().data("loginInfo", loginInfoVObyId);
        }
        return R.error().message("登录失败，请重新登录！");
    }
    //用户退出登录
    @PostMapping("logout")
    public R logoutMember(HttpServletRequest request){
        //调用jwt工具类，获取头部信息，返回用户id
        String sid = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据id获得登录用户信息
        LoginInfoVO logoutInfoVObyId = smqMemberService.getLoginInfoVObyId(Long.parseLong(sid));
        return R.ok().data("logoutInfo",logoutInfoVObyId);
    }

    //修改密码
    @PostMapping("updatepassword")
    public R updatePassword(@RequestBody UpdatePasswordVO updatePasswordVO, HttpServletRequest request){
            return   smqMemberService.UpdatePassword(updatePasswordVO,request);
    }

//    //根据用户id获取用户信息
//    @PostMapping("getUserInfoOrder/{id}")
//    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
//        SmqMember smqMember = smqMemberService.getById(id);
//        //把UcenterMember复制为UcenterMemberOrder对象
//        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
//        BeanUtils.copyProperties(ucenterMember,ucenterMemberOrder);
//        return ucenterMemberOrder;
//    }

//    @GetMapping("countRegister/{day}")
//    public R countRegister(@PathVariable String day){
//        Integer count = ucenterMemberService.ucenterMemberService(day);
//        return R.ok().data("countRegister",count);
//    }



}

