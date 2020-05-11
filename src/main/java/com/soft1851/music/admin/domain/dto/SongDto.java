package com.soft1851.music.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wl
 * @ClassNamesongDto
 * @Description TODO
 * @Date 2020/5/7
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongDto {

    private  String songname;
    private String singer;
    private  String url;
    private String thumbnail;
}
