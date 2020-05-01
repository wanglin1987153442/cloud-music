package com.soft1851.music.admin.exception;


import com.baomidou.mybatisplus.extension.api.R;
import com.soft1851.music.admin.common.Result;
import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.exception.CustomException;
import com.soft1851.music.admin.exception.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author mq_xu
 * @Date 2020/4/15
 * @Version 1.0
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 自定义异常的处理，统一在这里捕获返回JSON格式的友好提示
     *
     * @param exception
     * @return ResponseResult
     */
    @ExceptionHandler(value = {JwtException.class})
    @ResponseBody
    public Result sendError(JwtException exception) {
        log.error(exception.getMessage());
        return Result.failure(exception.getResultCode());
    }

    /**
     * NPE异常的处理
     *
     * @param exception
     * @return ResponseResult
     */
    @ExceptionHandler(value = {NullPointerException.class})
    @ResponseBody
    public Result sendError(NullPointerException exception) {
        log.error(exception.getMessage());
        return Result.failure(ResultCode.RESULT_CODE_DATA_NONE);
    }

    @ExceptionHandler(value = {CustomException.class})
    @ResponseBody
    public Result sendError(CustomException exception) {
        return Result.failure(exception.getResultCode());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result request405(HttpRequestMethodNotSupportedException ex) {
        return Result.failure(ResultCode.TOKEN_ERRO);
    }




        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseBody
        public Result hanleValidationExceptions(
                MethodArgumentNotValidException ex){
            Map<String,String>errors =new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error)->{
                String filedName=((FieldError)error).getField();
                String erroMessage=error.getDefaultMessage();
                errors.put(filedName,erroMessage);
            });
            return Result.failure(ResultCode.PARAM_IS_INVALID,errors);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        @ResponseBody
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public   Result handleConstraintViolationException(ConstraintViolationException e){
            return Result.failure(ResultCode.PARAM_IS_BLANK,e.getMessage());
        }


}