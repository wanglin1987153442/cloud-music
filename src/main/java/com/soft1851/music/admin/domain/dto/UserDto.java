package com.soft1851.music.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wl
 * @ClassNameuserDto
 * @Description TODO
 * @Date 2020/5/1
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private  String avatar;
    private  String password;
    private  String num;
}
