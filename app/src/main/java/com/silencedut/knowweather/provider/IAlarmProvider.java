package com.silencedut.knowweather.provider;

import com.silencedut.knowweather.ui.adapter.alarm.AlarmEntityData;
import com.silencedut.weather_core.api.ICoreApi;

/**
 * 这里才是有用的
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/18
 */

public interface IAlarmProvider extends ICoreApi {
    void insert(AlarmEntityData data);

    void delete(AlarmEntityData data);

    void update(AlarmEntityData data);
}
