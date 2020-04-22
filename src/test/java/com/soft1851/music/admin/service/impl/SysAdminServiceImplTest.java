package com.soft1851.music.admin.service.impl;

import com.soft1851.music.admin.dto.LoginDto;
import com.soft1851.music.admin.entity.RoleAdmin;
import com.soft1851.music.admin.mapper.RoleAdminMapper;
import com.soft1851.music.admin.service.SysAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SysAdminServiceImplTest {
@Resource
private SysAdminService sysAdminService;
@Resource
private RoleAdminMapper roleAdminMapper;
    @Test
    void login() {
        LoginDto loginDto =LoginDto.builder().name("mqxu").password("123456").build();
        sysAdminService.login(loginDto);
    }
    @Test
    void seectbyid() throws SQLException {
        List<String> roleId=roleAdminMapper.selectRoleById("DE35D7CC05AF96A21D7ADFC8651E6614");

        System.out.println(roleId);
    }
}