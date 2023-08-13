package com.smq.itemservice.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 库存明细表
 * </p>
 *
 * @author atguigu
 * @since 2023-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SmqStorage对象", description="库存明细表")
public class SmqStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "类目号")
    private Long itemId;

    @ApiModelProperty(value = "库存数量")
    private Integer storageNumber;

    @ApiModelProperty(value = "库存总量")
    private BigDecimal totalStorage;

    @ApiModelProperty(value = "位置id")
    private Long locationId;


    @TableField(fill = FieldFill.INSERT_UPDATE )
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除1（true）已删除，0（false）未删除")
    private Integer isDeleted;

}
