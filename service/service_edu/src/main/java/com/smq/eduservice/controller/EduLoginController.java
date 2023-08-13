package com.smq.eduservice.controller;

import com.smq.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author 彭云
 * @title: EduLoginController
 * @projectName guli_parent
 * @description: TODO
 * @date 2023/6/1811:06
 */
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {
//    login

    @PostMapping("/login")
    public R login(){
        return  R.ok().data(" token","admin");
    }
//    info
    @GetMapping("/info")
    public R info(){
        return  R.ok().data("name","admin").data("avatar","https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
    }
}
