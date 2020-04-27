package com.soft1851.music.admin.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author wl
 * @ClassNameExportDataAdapter
 *  @Description 数据缓冲区
 * @Date 2020/4/28
 * @Version 1.0
 */
public class ExportDataAdapter<T> {
    /**
     * 默认队列大小
     */

    private static Integer DEAULT_SIZE=1000;
    private BlockingQueue<T> resourceQueue = null;
    public ExportDataAdapter(){
        //阻塞队列
        this.resourceQueue=new LinkedBlockingDeque<T>(DEAULT_SIZE);

    }
    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(T data) {
        try {
            resourceQueue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取剩余数据数量
     *
     * @return
     */
    public Integer getDataSize(){
        return  resourceQueue.size();
    }
    /**
     * 从队列中获取数据
     */
    public T getData(){
        try {
            return resourceQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
