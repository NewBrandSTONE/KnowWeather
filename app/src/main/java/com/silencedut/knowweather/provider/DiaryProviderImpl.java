package com.silencedut.knowweather.provider;

import android.os.Handler;
import android.support.annotation.WorkerThread;

import com.silencedut.hub_annotation.HubInject;
import com.silencedut.knowweather.repository.IDiaryRepositoryApi;
import com.silencedut.knowweather.ui.adapter.diary.DiaryEntityData;
import com.silencedut.weather_core.CoreManager;

import java.util.List;

/**
 * 日记数据接口的具体实现类
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/13
 */
@HubInject(api = IDiaryProvider.class)
public class DiaryProviderImpl implements IDiaryProvider {

    @WorkerThread
    @Override
    public List<DiaryEntityData> searchDiary(String date) {
        return CoreManager.getImpl(IDiaryRepositoryApi.class).searchDiary(date);
    }

    @WorkerThread
    @Override
    public List<DiaryEntityData> searchAllDiary() {
        return CoreManager.getImpl(IDiaryRepositoryApi.class).searchAllDiary();
    }

    @Override
    public Handler getDiaryWorkHandler() {
        return CoreManager.getImpl(IDiaryRepositoryApi.class).getDiaryWorkHandler();
    }

    @Override
    public void saveCurrentDiary(DiaryEntityData entityData) {
        CoreManager.getImpl(IDiaryRepositoryApi.class).saveCurrentDiary(entityData);
    }

    @Override
    public void loadDiaryData() {
//        CoreManager.getImpl(IDiaryRepositoryApi.class).insertTestData();
    }

    @Override
    public void insertSportData(DiaryEntityData data) {
        CoreManager.getImpl(IDiaryRepositoryApi.class).insertTestData(data);
    }

    @Override
    public void deleteDiaryData(DiaryEntityData data) {
        CoreManager.getImpl(IDiaryRepositoryApi.class).deleteDiaryData(data);
    }

    @Override
    public void onCreate() {

    }
}
