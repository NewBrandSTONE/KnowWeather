package com.silencedut.knowweather.repository;

import android.support.annotation.WorkerThread;

import com.silencedut.knowweather.ui.adapter.mine.MineEntityData;
import com.silencedut.weather_core.api.ICoreApi;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/29
 */

public interface IMineRepositoryApi extends ICoreApi {
    /**
     * 获取所有闹钟数据
     *
     * @return 闹钟数据
     */
    @WorkerThread
    MineEntityData fetchMineInfo();

    void insert(MineEntityData data);

    void update(MineEntityData data);
}
