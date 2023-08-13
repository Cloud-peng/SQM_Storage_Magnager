package com.smq.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author 彭云
 * @title: DemoData
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/7/216:42
 */

public class DemoData {
    //设置表头名称
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;
    //设置表头名称
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
