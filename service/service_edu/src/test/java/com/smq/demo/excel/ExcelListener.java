package com.smq.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author 彭云
 * @title: ExcelListener
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/7/221:16
 */
//创建读取excel监听器
public class ExcelListener extends AnalysisEventListener<DemoData> {

    //一行一行去读取excle内容
    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {
        System.out.println("****"+data.getSname()+":"+data.getSno());
    }
    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext
            context) {
        System.out.println("表头信息："+headMap);
    }
    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
