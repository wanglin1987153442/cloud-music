package com.soft1851.music.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft1851.music.admin.common.Result;
import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.domain.dto.LoginDto;
import com.soft1851.music.admin.domain.entity.SysAdmin;
import com.soft1851.music.admin.domain.entity.SysRole;
import com.soft1851.music.admin.mapper.RoleAdminMapper;
import com.soft1851.music.admin.mapper.SysAdminMapper;
import com.soft1851.music.admin.service.SysAdminService;
import com.soft1851.music.admin.util.CreateToken;
import com.soft1851.music.admin.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wl
 * @since 2020-04-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysAdminServiceImpl extends ServiceImpl<SysAdminMapper, SysAdmin> implements SysAdminService {
    @Resource
    private SysAdminMapper sysAdminMapper;
    @Resource
    private RoleAdminMapper roleAdminMapper;

    /**
     * 登陆
     *
     * @param loginDto
     * @return boolean
     */

    @Override
    public Result login(LoginDto loginDto) {
        QueryWrapper<SysAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginDto.getName());
        SysAdmin user = sysAdminMapper.selectOne(queryWrapper);
        List<String> roleId = null;
        if (user != null) {
            if (user.getStatus().equals(1)) {
                if (user.getPassword().equals(Md5Util.getMd5(loginDto.getPassword(), true, 32))) {
//                    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//                    assert sra != null;
//                    HttpServletResponse response = sra.getResponse();
//                    response.setHeader("Authorization", token);
                    List<SysRole> sysRole = roleAdminMapper.selectRole(user.getId());
                        String token = CreateToken.getToken(user.getName(), JSONObject.toJSONString(sysRole),user.getSalt());
                        Map<String, Object> map = new TreeMap<>();
                        map.put("userId", user.getId());
                        map.put("username", user.getName());
                        map.put("awatar", user.getAvatar());
                        map.put("token", token);
                    for (SysRole sysRole1 : sysRole) {
                        map.put("userRole", sysRole);
                    }
                        return Result.success(map);

                } else {
                    log.info("密码错误");
                    return Result.failure(ResultCode.USER_PASSWORD_ERROR);
                }
            } else {
                return Result.failure(ResultCode.USER_STATUS_ISNO);
            }
        }
        log.info("用户不存在");
        return Result.failure(ResultCode.USER_NOT_EXIST);


    }


}
