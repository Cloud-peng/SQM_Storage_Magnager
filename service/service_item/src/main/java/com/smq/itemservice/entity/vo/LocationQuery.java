package com.smq.itemservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 彭云
 * @title: ItemQuery
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/7/1720:51
 */
@ApiModel(value = "Location查询对象", description = "位置对象封装")
@Data
public class LocationQuery {
    private static final long serialVersionUID = 1L;
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