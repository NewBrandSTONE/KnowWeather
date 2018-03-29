package com.silencedut.knowweather.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;

import com.silencedut.hub_annotation.HubInject;
import com.silencedut.knowweather.repository.db.AlarmDatabase;
import com.silencedut.knowweather.ui.adapter.alarm.AlarmEntityData;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.repository.DBHelper;

import java.util.List;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/18
 */
@HubInject(api = IAlarmRepositoryApi.class)
public class AlarmRepositoryImpl implements IAlarmRepositoryApi {

    private static final String TAG = "DiaryRepositoryImpl";
    private static final String ALARM_DB_NAME = "alarm";
    private AlarmDatabase mDiaryDatabase;
    private Handler mHandler;
    private MutableLiveData<List<AlarmEntityData>> mDatas;

    @Override
    public List<AlarmEntityData> fetchAllAlarm() {
        return mDiaryDatabase.dao().getAll();
    }

    @Override
    public void insert(final AlarmEntityData data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mDiaryDatabase.dao().insertDiary(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void delete(AlarmEntityData data) {
        mDiaryDatabase.dao().delete(data);
    }

    @Override
    public void update(AlarmEntityData data) {
        mDiaryDatabase.dao().update(data);
    }

    @Override
    public void onCreate() {
        mDiaryDatabase = DBHelper.provider(AlarmDatabase.class, ALARM_DB_NAME);
        mHandler = TaskScheduler.provideHandler(TAG);
        mDatas = new MutableLiveData<>();
    }
}
