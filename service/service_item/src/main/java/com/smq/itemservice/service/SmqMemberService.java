package com.smq.itemservice.service;

import com.smq.commonutils.R;
import com.smq.itemservice.entity.SmqMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smq.itemservice.entity.vo.LoginInfoVO;
import com.smq.itemservice.entity.vo.UpdatePasswordVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 人员表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-08-06
 */
public interface SmqMemberService extends IService<SmqMember> {

    R login(SmqMember smqMember);

    LoginInfoVO getLoginInfoVObyId(long id);
    R UpdatePassword(UpdatePasswordVO updatePasswordVO, HttpServletRequest request);
}
