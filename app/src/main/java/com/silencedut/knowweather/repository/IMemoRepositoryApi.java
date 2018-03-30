package com.silencedut.knowweather.repository;

import android.support.annotation.WorkerThread;

import com.silencedut.knowweather.ui.adapter.memo.MemoEntityData;
import com.silencedut.weather_core.api.ICoreApi;

import java.util.List;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/30
 */

public interface IMemoRepositoryApi extends ICoreApi {
    /**
     * 获取所有闹钟数据
     *
     * @return 闹钟数据
     */
    @WorkerThread
    List<MemoEntityData> fetchAll();

    void insert(MemoEntityData data);

    void delete(MemoEntityData data);
}
