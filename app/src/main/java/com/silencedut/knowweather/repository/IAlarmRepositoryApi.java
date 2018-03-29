package com.silencedut.knowweather.repository;

import android.support.annotation.WorkerThread;

import com.silencedut.knowweather.ui.adapter.alarm.AlarmEntityData;
import com.silencedut.weather_core.api.ICoreApi;

import java.util.List;

/**
 * 闹钟
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/17
 */

public interface IAlarmRepositoryApi extends ICoreApi {

    /**
     * 获取所有闹钟数据
     *
     * @return 闹钟数据
     */
    @WorkerThread
    List<AlarmEntityData> fetchAllAlarm();

    void insert(AlarmEntityData data);

    void delete(AlarmEntityData data);

    void update(AlarmEntityData data);
}
