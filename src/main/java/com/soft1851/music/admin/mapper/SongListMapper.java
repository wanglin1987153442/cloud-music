package com.soft1851.music.admin.mapper;

import com.soft1851.music.admin.domain.entity.SongList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft1851.music.admin.domain.vo.songListVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
public interface SongListMapper extends BaseMapper<SongList> {
@Select("SELECT song_list_name as name , like_count as value FROM `song_list` GROUP BY like_count DESC LIMIT 20")
    public List<songListVo> getLikeCount();
}
