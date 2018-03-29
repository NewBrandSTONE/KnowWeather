package com.silencedut.knowweather.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import com.silencedut.knowweather.repository.db.AlarmDatabase;
import com.silencedut.knowweather.ui.adapter.alarm.AlarmEntityData;
import com.silencedut.knowweather.ui.adapter.diary.DiaryEntityData;
import com.silencedut.taskscheduler.Task;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.repository.DBHelper;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/18
 */

public class AlarmRepository {
    private static final String TAG = "AlarmRepository";
    private static final String ALARM_DB_NAME = "alarm";
    private AlarmDatabase mAlarmDatabase;
    private static final AtomicReference<AlarmRepository> INSTANCE_REFERENCE = new AtomicReference<>();

    private MutableLiveData<List<AlarmEntityData>> mAlarmLiveData;
    private Handler mAlarmWorkHandler;

    private AlarmRepository() {
        mAlarmDatabase = DBHelper.provider(AlarmDatabase.class, ALARM_DB_NAME);
        mAlarmWorkHandler = TaskScheduler.provideHandler(TAG);
        mAlarmLiveData = new MutableLiveData<>();
    }

    public static AlarmRepository getInstance() {
        for (; ; ) {
            AlarmRepository current = INSTANCE_REFERENCE.get();
            if (current != null) {
                return current;
            }
            current = new AlarmRepository();
            if (INSTANCE_REFERENCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    public Handler getDirayWorkHandler() {
        return mAlarmWorkHandler;
    }

    @MainThread
    public MutableLiveData<List<AlarmEntityData>> getAlarmObserver() {
        return mAlarmLiveData;
    }

    @Nullable
    public List<AlarmEntityData> getCachedWeatherData() {
        return mAlarmLiveData.getValue();
    }


}
