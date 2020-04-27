package com.soft1851.music.admin.intercepter;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.domain.entity.SysAdmin;
import com.soft1851.music.admin.domain.entity.SysRole;
import com.soft1851.music.admin.exception.JwtException;
import com.soft1851.music.admin.mapper.SysAdminMapper;
import com.soft1851.music.admin.service.SysRoleService;
import com.soft1851.music.admin.util.CreateToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 拦截器 继承 HandlerInterceptor 接口
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysAdminMapper sysAdminMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String token = request.getHeader("Authorization");
        String userRole = CreateToken.getUserRole(token);
        List<SysRole> roleList = JSONArray.parseArray(userRole, SysRole.class);
        String roleId = request.getParameter("roleId");

        QueryWrapper<SysAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", CreateToken.getUserId(token));
        SysAdmin user = sysAdminMapper.selectOne(queryWrapper);
        String secret=user.getSalt();
        if (token == null) {
            throw new JwtException("用户未登录", ResultCode.USER_NOT_SIGN_IN);
        } else if (CreateToken.verify(token,secret) == false) {
            throw new JwtException("TOKEN错误", ResultCode.TOKEN_ERRO);
        } else if (CreateToken.checkTime(token) == false) {
            throw new JwtException("Token 过时", ResultCode.TOKEN_TIME_ERRO);
        } else if (sysRoleService.checkRole(roleList, Integer.parseInt(roleId)) == false) {
            throw new JwtException("权限不足", ResultCode.USER_NO_ENOUGHPOWER);
        } else {
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

