package com.smq.itemservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

/**
 * <p>
 * 人员表
 * </p>
 *
 * @author atguigu
 * @since 2023-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmqMember对象", description="人员表")
public class SmqMember implements Serializable {

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

    @TableField(fill = FieldFill.INSERT_UPDATE )
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "逻辑删除1（true）已删除，0（false）未删除")
    private Integer isDeleted;

    @Transient
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像地址")
    private String avatar;


}
