package com.soft1851.music.admin.controller;


import com.soft1851.music.admin.service.SongService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RequestMapping("/api/song")
public class SongController {
@Resource
   private SongService songService;


@GetMapping("/page")
    public Map<String, Object> getSongPage(@Param("page") Integer page){
    Map<String, Object> page1 = songService.getPage(page);
    return page1;

}
    @GetMapping(value = "/export")
    public void exportData() {
        songService.exportData();
    }


}
