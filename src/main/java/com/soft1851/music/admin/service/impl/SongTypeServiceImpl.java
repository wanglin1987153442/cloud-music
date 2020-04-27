package com.soft1851.music.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft1851.music.admin.domain.entity.SongType;
import com.soft1851.music.admin.mapper.SongTypeMapper;
import com.soft1851.music.admin.service.SongTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
@Service
public class SongTypeServiceImpl extends ServiceImpl<SongTypeMapper, SongType> implements SongTypeService {
    @Resource
    private SongTypeMapper songTypeMapper;

    @Override
    public Map<String, Object> selectAllType(Integer num) {
        IPage<SongType> page = new Page<>(num, 20);
        QueryWrapper<SongType> wrapper = new QueryWrapper<>();
        IPage<SongType> page1 = songTypeMapper.selectPage(page, wrapper);
        List<SongType> records = page1.getRecords();
        long pages = page1.getPages();
        Map<String, Object> map = new HashMap<>();
        map.put("totlpage", pages);
        map.put("records", records);
        return map;

    }
}
