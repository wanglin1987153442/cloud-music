package com.soft1851.music.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.soft1851.music.admin.common.Result;
import com.soft1851.music.admin.domain.entity.SongList;
import com.soft1851.music.admin.domain.vo.songListVo;

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
     *
     * @param field
     * @return
     */
    List<SongList> blurSelect(String field);

    /**
     * 分页
     *
     * @param num
     * @return
     */
    Map<String, Object> getByPage(Long num);

    /**
     * 删除类型歌曲
     *
     * @param songListId
     * @return
     */
    Result deleteSongType(List<String> songListId);

    /**
     * 查出收听率前二十的歌曲
     *
     * @return
     */
    List <SongList> getFrontList();


    List<songListVo> getFrontLike();

}
