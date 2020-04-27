package com.soft1851.music.admin.controller;


import com.soft1851.music.admin.service.SongTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
@RestController
@RequestMapping("/api/songType")
public class SongTypeController {
    @Resource
    private SongTypeService songTypeService;

    @GetMapping("/Type")
    public Map<String, Object> getAlltype(Integer num) {
        return songTypeService.selectAllType(num);
    }
}
