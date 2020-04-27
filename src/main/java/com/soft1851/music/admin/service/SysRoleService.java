package com.soft1851.music.admin.service;

import com.soft1851.music.admin.common.Result;
import com.soft1851.music.admin.domain.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 查询角色所有子菜单
     * @param roleId
     * @return
     */
    Result selectRoleById(int roleId);

    /**
     * 检验role 于token role是否能比对
     * @param roles
     * @param roleId
     * @return
     */
    boolean checkRole(List<SysRole>roles,int roleId);

}
