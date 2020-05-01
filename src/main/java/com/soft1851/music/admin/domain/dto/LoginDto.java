package com.soft1851.music.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author wl
 * @ClassNameLoginDto
 * @Description TODO
 * @Date 2020/4/21
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    /**
     * 手机号
     */
    @NotBlank(message = "帐号不能为空")

    private String name;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 4, max = 12 ,message = "密码范围4~12位")
    private String password;
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")

    private String verifyCode;
    /**
     * 获取验证码所需要的uuid
     */
    private String uuid;
}
