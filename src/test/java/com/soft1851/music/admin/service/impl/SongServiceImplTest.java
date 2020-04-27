package com.soft1851.music.admin.service.impl;

import com.soft1851.music.admin.service.SongService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Map;

@SpringBootTest
class SongServiceImplTest {
@Resource
private SongService songService;
    @Test
    void getPage() {
        Map<String,Object>page = songService.getPage(1);
        System.out.println(page);
    }
}