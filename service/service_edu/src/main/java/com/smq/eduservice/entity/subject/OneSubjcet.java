package com.smq.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 彭云
 * @title: OneSubjcet
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/7/623:12
 */
@Data
public class OneSubjcet {
    private String id;
    private String title;
//    一级分类有多个二级分类
    private List<TwoSubject> children=new ArrayList<>();
}
