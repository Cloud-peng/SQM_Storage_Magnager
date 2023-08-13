package com.smq.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 彭云
 * @title: GuliException
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/5/2818:42
 */
@Data
@AllArgsConstructor//生成有参构造
@NoArgsConstructor//生成无参构造
public class GuliException extends RuntimeException {
    private  Integer code;//状态码
    private String msg;//异常信息
}
