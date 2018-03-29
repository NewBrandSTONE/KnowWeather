package com.silencedut.knowweather.viewmodel.alarm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import com.silencedut.knowweather.repository.AlarmRepository;
import com.silencedut.knowweather.repository.IAlarmRepositoryApi;
import com.silencedut.knowweather.ui.adapter.alarm.AlarmEntityData;
import com.silencedut.taskscheduler.Task;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.viewmodel.BaseViewModel;

import java.util.List;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/18
 */

public class AlarmViewModel extends BaseViewModel {

    private List<AlarmEntityData> mAlarmDatas;
    private MutableLiveData<List<AlarmEntityData>> mAlarm;

    @Override
    protected void onCreate() {
        mAlarm = new MutableLiveData<>();
        AlarmRepository.getInstance().getAlarmObserver().observe(this, new Observer<List<AlarmEntityData>>() {
            @Override
            public void onChanged(@Nullable List<AlarmEntityData> alarmEntityData) {
                try {
                    mAlarmDatas = alarmEntityData;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fetchAlarmData() {
        TaskScheduler.execute(new Task<List<AlarmEntityData>>() {
            @Override
            public List<AlarmEntityData> doInBackground() throws InterruptedException {
                return CoreManager.getImpl(IAlarmRepositoryApi.class).fetchAllAlarm();
            }

            @Override
            public void onSuccess(List<AlarmEntityData> datas) {
                parseData(datas);
            }
        });
    }

    @MainThread
    private void parseData(List<AlarmEntityData> datas) {
        List<AlarmEntityData> data = datas;
        mAlarmDatas = data;
        mAlarm.postValue(data);
    }

    public void insertData(String time, int enable) {
        AlarmEntityData data = new AlarmEntityData();
        data.setEnable(enable);
        data.setTime(time);
        CoreManager.getImpl(IAlarmRepositoryApi.class).insert(data);
    }

    public void deleteData(AlarmEntityData data) {
        CoreManager.getImpl(IAlarmRepositoryApi.class).delete(data);
    }

    public MutableLiveData<List<AlarmEntityData>> getmAlarm() {
        return mAlarm;
    }
}
