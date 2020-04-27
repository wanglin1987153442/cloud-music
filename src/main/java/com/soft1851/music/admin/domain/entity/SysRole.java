package com.soft1851.music.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRole extends Model<SysRole> {
    private static final long serialVersionUID = 1L;
    /**
     * 查询用户拥有的子菜单  sysroleMapper所有
     */

    private List<SysMenu> menus;

    /**
     * 查询用户角色所用 对于sysAdminMapper
     */

    private List<SysRole> roles;
    /**
     * 主键
     */
    @TableId(value = "role_id", type = IdType.AUTO)

    private Integer roleId;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField("description")
    @JsonIgnore
    private String description;


    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

}
