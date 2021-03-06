package com.soft1851.music.admin.handler;

/**
 * @author wl
 * @ClassNamesda
 * @Description TODO
 * @Date 2020/4/21
 * @Version 1.0
 */

import com.soft1851.music.admin.common.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author mq_xu
 * @description 全局统一响应处理
 * @create 2020/2/5
 */

@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice {


    /**
     * 此处如果返回false , 则不执行当前Advice的业务
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * 处理响应的具体方法
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) {
            return body;
        } else {
            return Result.success(body);
        }
    }
}