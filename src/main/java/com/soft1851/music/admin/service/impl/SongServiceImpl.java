package com.soft1851.music.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft1851.music.admin.common.Result;
import com.soft1851.music.admin.domain.dto.SongDto;
import com.soft1851.music.admin.domain.entity.Song;
import com.soft1851.music.admin.mapper.SongMapper;
import com.soft1851.music.admin.service.SongService;
import com.soft1851.music.admin.util.ExcelConsumer;
import com.soft1851.music.admin.util.ExportDataAdapter;
import com.soft1851.music.admin.util.SnowFlake;
import com.soft1851.music.admin.util.ThreadPool;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yy
 * @since 2020-04-21
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@Slf4j

public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

@Resource
private SongMapper songMapper;
    @Override
    public Map<String,Object> getPage(Integer num) {
        IPage<Song>mypage=new Page<>(num,20);
        mypage=songMapper.selectPageVo((Page<?>) mypage);
        long pages = mypage.getPages();
        List<Song> list=mypage.getRecords();
       Map<String,Object>map=new HashMap<>();
       map.put("totlpage",pages);
       map.put("songList",list);
       return map;
    }

    @SneakyThrows
    @Override
    public void exportData() {
        String excelPath = "D:\\song.xlsx";
        //导出excel对象
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(1000);
        //数据缓冲
        ExportDataAdapter<Song> exportDataAdapter = new ExportDataAdapter<>();

        //线程同步对象
        CountDownLatch latch = new CountDownLatch(2);
        //启动线程获取数据(生产者)
      ThreadPool.getExecutor().submit(() -> produceExportData(exportDataAdapter, latch));

        //启动线程导出数据（消费者）
        ThreadPool.getExecutor().submit(() -> new ExcelConsumer<>(Song.class, exportDataAdapter, sxssfWorkbook, latch, "歌曲数据").run());
        latch.await();
        //使用字节流写数据
        OutputStream outputStream = new FileOutputStream(excelPath);
        sxssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }


    /**
     * 生产者生产数据
     *
     * @param exportDataAdapter
     * @param latch
     */
    private void produceExportData(ExportDataAdapter<Song> exportDataAdapter, CountDownLatch latch) {

        List<Song> songs = songMapper.selectList(null);

        songs.forEach(exportDataAdapter::addData);
        log.info("数据生产完成");
        //数据生产结束
        latch.countDown();
    }


    @Override
    public Result insertSong(SongDto songDto) {
        Song song = new Song();
        song.setSongId(String.valueOf(SnowFlake.getSnowFlake()));
        song.setSongName(songDto.getSongname());
        song.setSortId("0");
        song.setSinger(songDto.getSinger());
        song.setDuration("04:48");
        song.setThumbnail(songDto.getThumbnail());
        song.setUrl(songDto.getUrl());
        song.setLyric(null);
        song.setCommentCount(0);
        song.setLikeCount(0);
        song.setDeleteFlag("0");
        song.setUpdateTime(LocalDateTime.now());
        song.setCreateTime(LocalDateTime.now());
        int insert = songMapper.insert(song);
        if (insert != 0) {
            return Result.success();
        }

        return null;
    }

}
