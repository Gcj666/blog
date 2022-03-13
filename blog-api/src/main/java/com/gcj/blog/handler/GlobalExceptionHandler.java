package com.gcj.blog.handler;

import com.gcj.blog.vo.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//表示在Controller上执行
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R handler(Exception ex){
        ex.printStackTrace();
        return R.fail(-999,"系统异常");
    }
}
