package com.soft1851.music.admin.util;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author wl
 * @ClassNameThreadPool
 * @Description 线程池
 * @Date 2020/4/27
 * @Version 1.0
 */
@Component
public class ThreadPool {
/**
 * 异步线程
 */
    /**
     * 阻塞线程 new ArrayBlockingQueue 如果队列已经满了，那么插入的操作就会被阻塞
     */
    private final static ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 100, 10,
            TimeUnit.MINUTES, new ArrayBlockingQueue<>(2000),
            //继承了runnable接口
            r -> new Thread(r, "excelExportThread"),
            new ThreadPoolExecutor.AbortPolicy());

    public static ThreadPoolExecutor getExecutor() {
        return executor;
    }







}

