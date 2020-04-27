package com.soft1851.music.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft1851.music.admin.domain.entity.RoleAdmin;
import com.soft1851.music.admin.domain.entity.SysRole;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
public interface RoleAdminMapper extends BaseMapper<RoleAdmin> {

 List<SysRole> selectRole(String id);
}
