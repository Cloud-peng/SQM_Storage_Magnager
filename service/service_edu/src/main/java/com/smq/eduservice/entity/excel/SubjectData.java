package com.smq.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 彭云
 * @title: SubjectData
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/7/222:15
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubectName;

    @ExcelProperty(index = 1)
    private String twoSubectName;
}
