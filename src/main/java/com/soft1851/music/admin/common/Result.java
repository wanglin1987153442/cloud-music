package com.soft1851.music.admin.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wl
 * @ClassNamedadas
 * @Description TODO
 * @Date 2020/1/1
 * @Version 1.0
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = -3948389268046368059L;
    private Integer code;
    private String msg;
    //注解可以隐藏 返回值中的null值
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;


    public Result() {

    }
    
    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }


    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }




    public static Result failure(ResultCode resultCode, Object data) {

        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }
    public static Result failure(ResultCode resultCode) {

        Result result =new Result();
        result.setResultCode(resultCode);


        return result;
    }



    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }

}