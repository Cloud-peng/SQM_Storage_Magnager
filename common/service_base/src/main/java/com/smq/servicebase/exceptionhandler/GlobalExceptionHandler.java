package com.smq.servicebase.exceptionhandler;


import com.smq.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 彭云
 * @title: GlobalExceptionHandler
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/5/2817:33
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //    指定出现什么异常执行此方法
    @ExceptionHandler(Exception.class)
    @ResponseBody//为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //    指定出现ArithmeticException执行此方法
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//为了返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }
    //  自定义异常
    //  指定出现GuliException执行此方法
    @ExceptionHandler(GuliException.class)
    @ResponseBody//为了返回数据
    public R error(GuliException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
