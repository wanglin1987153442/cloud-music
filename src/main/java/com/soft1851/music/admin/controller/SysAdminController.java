package com.soft1851.music.admin.controller;


import com.soft1851.music.admin.common.Result;
import com.soft1851.music.admin.domain.dto.LoginDto;
import com.soft1851.music.admin.service.SysAdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wl
 * @since 2020-04-21
 */
@RestController
@RequestMapping("/api/sysAdmin")
public class SysAdminController {
    @Resource
    private SysAdminService sysAdminService;
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto){

        return  sysAdminService.login(loginDto);
    }


}
