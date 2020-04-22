package com.soft1851.music.admin.intercepter;


import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.exception.JwtException;
import com.soft1851.music.admin.util.CreateToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器 继承 HandlerInterceptor 接口
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new JwtException("用户未登录", ResultCode.USER_NOT_SIGN_IN);

        }  else if (CreateToken.verify(token) == false) {
            throw new JwtException("TOKEN错误", ResultCode.TOKEN_ERRO);

        } else if (CreateToken.checkTime(token) == false) {
            throw new JwtException("Token 过时", ResultCode.TOKEN_TIME_ERRO);
        }
else {
            return true;
        }

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}

