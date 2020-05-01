package com.soft1851.music.admin.mapper;

import com.soft1851.music.admin.domain.dto.UserDto;
import com.soft1851.music.admin.domain.entity.SysAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.sql.SQLException;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
public interface SysAdminMapper extends BaseMapper<SysAdmin> {
    /**
     * 更新学生信息
     * @param sysAdmin
     * @throws SQLException
     */
    @Update({
            "<script>",
            "UPDATE sys_admin",
            "<set>",
            "<if test = 'userDto.avatar !=null'>",
            "avatar=#{userDto.avatar}, ",
            "</if>",
            "<if test = 'userDto.password !=null'>",
            "password=#{userDto.password}, ",
            "</if>",
            "</set>",
            "where name =#{userDto.num}",
            "</script>"
    })
    int updateUserInformation(@Param("userDto") UserDto userDto)throws SQLException;

}
