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
@ApiModel(value="Record查询对象", description="封装查询对象")
public class RecordQuery {
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
    @ApiModelProperty(value = "批号")
    private String batchNumber;
//    @ApiModelProperty(value = "CAS号")
//    private String casNumber;
    @ApiModelProperty(value = "物质状态(液体、固体、气体)")
    private String matter;

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

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;


}
