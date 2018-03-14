package com.silencedut.knowweather.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;

import com.silencedut.knowweather.repository.db.DiaryDatabase;
import com.silencedut.hub_annotation.HubInject;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.knowweather.ui.adapter.diary.DiaryEntityData;
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
    public void insertTestData() {
        mCityHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    DiaryEntityData data = new DiaryEntityData();
                    data.setContent("测试内容");
                    data.setRecordDate("测试记录日期");
                    data.setType("跑步");
                    mDiaryDatabase.diaryDao().insertDiary(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
