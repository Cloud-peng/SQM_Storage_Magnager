package com.smq.itemservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 彭云
 * @title: StorageQuery
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/7/2919:32
 */
@ApiModel(value = "Storage查询对象", description = "库存对象封装")
@Data
public class StorageQuery {
    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "包装方式")
    private String packing;

    @ApiModelProperty(value = "批号")
    private String batchNumber;

    @ApiModelProperty(value = "CAS号")
    private String casNumber;

    @ApiModelProperty(value = "物质状态(液体、固体、气体)")
    private String matter;

    @ApiModelProperty(value = "库存数量")
    private Integer storageNumber;

    @ApiModelProperty(value = "库存总量")
    private BigDecimal totalStorage;

    @ApiModelProperty(value = "库存预警值")
    private Integer alertNumber;

    @ApiModelProperty(value = "最大允许库存")
    private Integer allowStorage;

    @ApiModelProperty(value = "位置名称")
    private String location;

    @ApiModelProperty(value = "所属基地")
    private String basement;

    @ApiModelProperty(value = "1,可用；0不可用")
    private Integer isUseful;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;

}
