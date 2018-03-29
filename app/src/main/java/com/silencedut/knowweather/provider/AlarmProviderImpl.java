package com.silencedut.knowweather.provider;

import com.silencedut.hub_annotation.HubInject;
import com.silencedut.knowweather.repository.IAlarmRepositoryApi;
import com.silencedut.knowweather.ui.adapter.alarm.AlarmEntityData;
import com.silencedut.weather_core.CoreManager;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/19
 */
@HubInject(api = IAlarmProvider.class)
public class AlarmProviderImpl implements IAlarmProvider {
    @Override
    public void insert(AlarmEntityData data) {
        CoreManager.getImpl(IAlarmRepositoryApi.class).insert(data);
    }

    @Override
    public void delete(AlarmEntityData data) {
        CoreManager.getImpl(IAlarmRepositoryApi.class).delete(data);
    }

    @Override
    public void update(AlarmEntityData data) {
        CoreManager.getImpl(IAlarmRepositoryApi.class).update(data);
    }

    @Override
    public void onCreate() {

    }
}
