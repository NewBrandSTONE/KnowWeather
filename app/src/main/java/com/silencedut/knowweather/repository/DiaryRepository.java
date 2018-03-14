package com.silencedut.knowweather.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.silencedut.knowweather.repository.db.DiaryDatabase;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.knowweather.ui.adapter.diary.DiaryEntityData;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;
import com.silencedut.weather_core.corebase.StatusDataResource;
import com.silencedut.weather_core.repository.DBHelper;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 日记仓库操作对象
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/13
 */

public class DiaryRepository {
    private static final String TAG = "DiaryRepository";

    private final static String DIARY_DB_NAME = "diary";
    private DiaryDatabase mDiaryDatabase;
    private static final AtomicReference<DiaryRepository> INSTANCE_REFERENCE = new AtomicReference<>();

    private MutableLiveData<List<DiaryEntityData>> mDiaryDataLiveData;
    private Handler mDiaryWorkHandler;

    private DiaryRepository() {
        mDiaryDatabase = DBHelper.provider(DiaryDatabase.class, DIARY_DB_NAME);
        mDiaryWorkHandler = TaskScheduler.provideHandler(TAG);
        mDiaryDataLiveData = new MutableLiveData<>();
    }

    public static DiaryRepository getInstance() {
        for (; ; ) {
            DiaryRepository current = INSTANCE_REFERENCE.get();
            if (current != null) {
                return current;
            }
            current = new DiaryRepository();
            if (INSTANCE_REFERENCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    public Handler getDirayWorkHandler() {
        return mDiaryWorkHandler;
    }

    @MainThread
    public MutableLiveData<List<DiaryEntityData>> getDiaryObserver() {
        return mDiaryDataLiveData;
    }

    @Nullable
    public List<DiaryEntityData> getCachedWeatherData() {
        return mDiaryDataLiveData.getValue();
    }


    @MainThread
    public void saveWeatherAsync(final String cityId, final StatusDataResource<WeatherData> statusDataResource) {
        mDiaryWorkHandler.post(new Runnable() {
            @Override
            public void run() {
                updateDiary(cityId, statusDataResource);
            }
        });
    }

    @WorkerThread
    public void updateDiary(String cityId, final StatusDataResource<WeatherData> statusDataResource) {


//        if (StatusDataResource.Status.SUCCESS.equals(statusDataResource.status)) {
//            try {
//                WeatherData weatherData = statusDataResource.data;
//                Weather weather = new Weather();
//                weather.cityId = weatherData.getCityId();
//                weather.weatherJson = JsonHelper.toJson(weatherData);
//
//                mDiaryDatabase.diaryDao().insertDiary(weather);
//            } catch (Exception e) {
//                LogHelper.error(TAG, "updateWeather error %s", e);
//            }
//        } else if (StatusDataResource.Status.LOADING.equals(statusDataResource.status)) {
//            try {
//                WeatherData weatherData = JsonHelper.fromJson(mDiaryDatabase.dao().fetchWeather(cityId).weatherJson, WeatherData.class);
//                if (weatherData != null) {
//                    statusDataResource.data = weatherData;
//                }
//            } catch (Exception e) {
//                LogHelper.error(TAG, "no cache hit");
//            }
//
//        }
//
//        mWeatherDataLiveData.postValue(statusDataResource);
//        Router.instance().getReceiver(EventCenter.NotificationStatus.class).onUpdateNotification();

    }


    @MainThread
    public void deleteDiary(final String cityId) {
        if (cityId == null) {
            return;
        }
        mDiaryWorkHandler.post(new Runnable() {
            @Override
            public void run() {
//                mDiaryDatabase.diaryDao().deleteWeather(cityId);
            }
        });

    }

    @WorkerThread
    public List<DiaryEntityData> getFollowedWeather() {
        try {
            List<DiaryEntityData> all = mDiaryDatabase.diaryDao().getAll();
            return all;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
