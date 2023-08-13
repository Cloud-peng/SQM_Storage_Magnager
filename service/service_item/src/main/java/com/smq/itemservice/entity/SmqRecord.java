package com.smq.itemservice.entity;

import java.math.BigDecimal;

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

/**
 * <p>
 * 人员表
 * </p>
 *
 * @author atguigu
 * @since 2023-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmqRecord对象", description="人员表")
public class SmqRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "库存编号")
    private Long storageId;

    @ApiModelProperty(value = "操作人员id")
    private Long memberId;

    @ApiModelProperty(value = "操作类型,(出库)，（入库）")
    private String opreation;

    @ApiModelProperty(value = "操作数量(以包装计)")
    private Integer storageNumber;

    @ApiModelProperty(value = "操作数量(以单位计)")
    private BigDecimal totalStorage;

    @TableField(fill = FieldFill.INSERT_UPDATE )
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "逻辑删除1（true）已删除，0（false）未删除")
    private Integer isDeleted;


}
