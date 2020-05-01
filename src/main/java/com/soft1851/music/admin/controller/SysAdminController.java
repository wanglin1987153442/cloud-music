package com.soft1851.music.admin.controller;


import com.soft1851.music.admin.annotation.ControllerWebLog;
import com.soft1851.music.admin.common.Result;
import com.soft1851.music.admin.domain.dto.LoginDto;
import com.soft1851.music.admin.domain.dto.UserDto;
import com.soft1851.music.admin.service.SysAdminService;
import com.soft1851.music.admin.util.AliOssUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wl
 * @since 2020-04-21
 */
@RestController
@Validated
@RequestMapping("/api/sysAdmin")
public class SysAdminController {
    @Resource
    private SysAdminService sysAdminService;

    @PostMapping("/login")
    public Result login(@RequestBody @Valid LoginDto loginDto) {

        return sysAdminService.login(loginDto);
    }

    @PostMapping("information")

    @ControllerWebLog(name = "验证码", isSaved = true)
    public Result updateInformation(@RequestBody @Valid UserDto userDto) {
        return sysAdminService.updateInfomation(userDto);
    }


    @PostMapping("avatar")
    public Result updateAvatar(@RequestBody @Valid UserDto userDto, MultipartFile[] file) {
        List<String> upload = AliOssUtil.upload(file);
        upload.forEach(System.out::println);
        return null;

    }
}
