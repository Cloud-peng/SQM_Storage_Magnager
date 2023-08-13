package com.smq.itemservice.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LonginInfoVO对象", description="返回给前端的登录用户信息")
public class LoginInfoVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "工号")
    private String employNumber;

    @ApiModelProperty(value = "姓名")
    private String employName;

    @ApiModelProperty(value = "所属部门")
    private String department;

    @ApiModelProperty(value = "所属小组")
    private String team;

    @ApiModelProperty(value = "是否禁用1（true）可用，0（false）禁用")
    private Integer isUseful;

    @ApiModelProperty(value = "头像地址")
    private String avatar;


}
