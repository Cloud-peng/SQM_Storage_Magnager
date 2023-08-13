package com.smq.demo.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 彭云
 * @title: TestEasyExcel
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/7/216:45
 */

public class TestEasyExcel {
    @Test
    public  void testExcle() {
        // 实现Excel写操作  写法1
        String fileName = "D:\\write.xlsx";
//// 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//// 如果这里想使用03 则 传入excelType参数即可
//        EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(data());


//        实现Excel读操作
    EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();


    }
//    创建方法返回list集合
    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }
}
