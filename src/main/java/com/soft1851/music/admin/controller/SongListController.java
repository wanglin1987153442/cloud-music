package com.soft1851.music.admin.controller;


import com.soft1851.music.admin.domain.entity.SongList;
import com.soft1851.music.admin.service.SongListService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
@RestController
@RequestMapping("/api/songList")
public class SongListController {
    @Resource
    private SongListService songListService ;

    @GetMapping("/all")
    public List<Map<String, Object>>getByType(){
        return songListService.getByType();
    }


    @GetMapping("/serach")
    public List<SongList> blurSelect(String field){
        return songListService.blurSelect(field);
    }
    @PostMapping("/page")
    public Map<String, Object> getSongListByPage(@Param("page") Long page){
        return songListService.getByPage(page);
    }

}
