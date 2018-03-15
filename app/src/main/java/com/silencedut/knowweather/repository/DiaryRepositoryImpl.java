package com.silencedut.knowweather.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;

import com.silencedut.hub_annotation.HubInject;
import com.silencedut.knowweather.repository.db.DiaryDatabase;
import com.silencedut.knowweather.ui.adapter.diary.DiaryEntityData;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.repository.DBHelper;

import java.util.List;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/13
 */
@HubInject(api = IDiaryRepositoryApi.class)
public class DiaryRepositoryImpl implements IDiaryRepositoryApi {

    private static final String TAG = "DiaryRepositoryImpl";
    private static final String DIARY_DB_NAME = "diary";
    private DiaryDatabase mDiaryDatabase;
    private Handler mCityHandler;
    private MutableLiveData<List<DiaryEntityData>> mDatas;

    @Override
    public void onCreate() {
        mDiaryDatabase = DBHelper.provider(DiaryDatabase.class, DIARY_DB_NAME);
        mCityHandler = TaskScheduler.provideHandler(TAG);
        mDatas = new MutableLiveData<>();
    }

    @Override
    public List<DiaryEntityData> searchDiary(String date) {
        try {
            return mDiaryDatabase.diaryDao().queryDiaryByDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DiaryEntityData> searchAllDiary() {
        return mDiaryDatabase.diaryDao().getAll();
    }

    @Override
    public Handler getDiaryWorkHandler() {
        return null;
    }

    @Override
    public void saveCurrentDiary(DiaryEntityData entityData) {

    }

    @Override
    public void insertTestData(final DiaryEntityData data) {
        mCityHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mDiaryDatabase.diaryDao().insertDiary(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void deleteDiaryData(final DiaryEntityData data) {
        mCityHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mDiaryDatabase.diaryDao().delete(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
