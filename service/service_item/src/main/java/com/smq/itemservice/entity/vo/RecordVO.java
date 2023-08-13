package com.smq.itemservice.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 彭云
 * @title: StorageVO
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/7/2820:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SmqRecord视图层对象", description="类目表+位置表+操作人员+操作数量")
public class RecordVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "产品名称")
    private String name;
    @ApiModelProperty(value = "类别")
    private String category;
    @ApiModelProperty(value = "等级")
    private String grade;
    @ApiModelProperty(value = "品牌")
    private String brand;
    @ApiModelProperty(value = "规格")
    private Integer specification;
    @ApiModelProperty(value = "批号")
    private String batchNumber;
//    @ApiModelProperty(value = "CAS号")
//    private String casNumber;
    @ApiModelProperty(value = "物质状态(液体、固体、气体)")
    private String matter;
    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "包装方式")
    private String packing;

    @ApiModelProperty(value = "位置名称")
    private String location;

    @ApiModelProperty(value = "所属基地")
    private String basement;

    @ApiModelProperty(value = "操作类型,(出库)，（入库）")
    private String opreation;

    @ApiModelProperty(value = "操作数量(以包装计)")
    private Integer storageNumber;

    @ApiModelProperty(value = "操作数量(以单位计)")
    private BigDecimal totalStorage;

    @ApiModelProperty(value = "工号")
    private String employNumber;

    @ApiModelProperty(value = "姓名")
    private String employName;


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
