package com.soft1851.music.admin.mapper;

import com.soft1851.music.admin.entity.RoleAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
public interface RoleAdminMapper extends BaseMapper<RoleAdmin> {
 @Select("SELECT role_id FROM `role_admin` WHERE `admin_id`=#{id}")
 List<String> selectRoleById(String id)throws SQLException;
}
