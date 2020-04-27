package com.soft1851.music.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft1851.music.admin.domain.entity.SongList;
import com.soft1851.music.admin.mapper.SongListMapper;
import com.soft1851.music.admin.service.SongListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
     * <p>
     *  服务实现类
     * </p>
     *
     * @author su
     * @since 2020-04-21
     */
    @Service
    public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {

        @Resource
        private SongListMapper songListMapper;

        /**
         * 根据type字段进行分组，将每种类型的所有歌单作为该类型的子菜单
         * @return
         */
        @Override
        public List<Map<String, Object>> getByType() {
            QueryWrapper<SongList> wrapper = new QueryWrapper<>();
            //根据type字段进行分组，按照play_count进行降序排列
            wrapper.select("type").groupBy("type").orderByDesc("play_counts");
            List<Map<String, Object>> maps = songListMapper.selectMaps(wrapper);

            for (Map<String, Object>map : maps){
                if ("0".equals(map.get("type"))){
                    list().remove(map);
                }else {
                    QueryWrapper<SongList> wrapper1 = new QueryWrapper<>();
                    //根据父类的type类型查询属于该类型的数据
                    wrapper1.orderByDesc("play_counts").eq("type", map.get("type"));
                    List<Map<String, Object>> songLists = songListMapper.selectMaps(wrapper1);
                    map.put("child", songLists);

                }
            }

            return maps;
        }

        /**
         * 根据（name、type )属性进行模糊查询
         * @param field
         * @return
         */
        @Override
                public List<SongList> blurSelect(String field) {
            QueryWrapper<SongList> wrapper = new QueryWrapper<>();
            //like = like %变量%， leftLike = like %变量 rightLike = like 变量%
            wrapper.like("song_list_name", field).or().like("type", field);
            return songListMapper.selectList(wrapper);
        }


        @Override
        public Map<String,Object> getByPage(Long num) {
           IPage<SongList>page=new Page<SongList>(num,20);
            QueryWrapper<SongList> wrapper = new QueryWrapper<>();
            IPage<SongList> iPage = songListMapper.selectPage(page, wrapper);
            long pages = iPage.getPages();
            List<SongList> records = iPage.getRecords();
            Map<String,Object>map =new HashMap<>();
            map.put("totlpage",pages);
            map.put("records",records);
            return map;
        }

    }