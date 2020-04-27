package com.soft1851.music.admin.controller;


import com.soft1851.music.admin.common.Result;
import com.soft1851.music.admin.service.SysRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
@RestController
@RequestMapping("/api/sysRole")
public class SysRoleController {
@Resource
    private SysRoleService sysRoleService;

@GetMapping("/role")
    public Result getRoleById(@Param("roleId") int roleId){
    return sysRoleService.selectRoleById(roleId);
}

}
