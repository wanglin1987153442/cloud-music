package com.soft1851.music.admin.service;

import com.soft1851.music.admin.domain.entity.SongList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
public interface SongListService extends IService<SongList> {

    /**
     * 根据type字段进行分组，将每种类型的所有歌单作为该类型的子菜单
     * @return
     */
    List<Map<String, Object>> getByType();

    /**
     * 根据（name、type )属性进行模糊查询
     * @param field
     * @return
     */
    List<SongList> blurSelect(String field);

//
Map<String,Object> getByPage(Long num);
}
