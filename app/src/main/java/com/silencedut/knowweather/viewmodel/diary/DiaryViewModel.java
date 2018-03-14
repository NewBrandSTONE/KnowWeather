package com.silencedut.knowweather.viewmodel.diary;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import com.silencedut.knowweather.provider.IDiaryProvider;
import com.silencedut.knowweather.repository.DiaryRepository;
import com.silencedut.knowweather.repository.IDiaryRepositoryApi;
import com.silencedut.knowweather.ui.adapter.diary.DiaryEntityData;
import com.silencedut.taskscheduler.Task;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.viewmodel.BaseViewModel;

import java.util.List;

/**
 * 日记ViewModel
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/13
 */

public class DiaryViewModel extends BaseViewModel {

    private static final String TAG = DiaryViewModel.class.getSimpleName();
    private MutableLiveData<List<DiaryEntityData>> mDiary = new MutableLiveData<>();
    private List<DiaryEntityData> mDiaryDatas;

    @Override
    protected void onCreate() {
        // 状态数据初始化
        mDiary = new MutableLiveData<>();
        // 调用日记仓库获取日记数据
        DiaryRepository.getInstance().getDiaryObserver().observe(this, new Observer<List<DiaryEntityData>>() {
            @Override
            public void onChanged(@Nullable List<DiaryEntityData> diaryEntityData) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDiaryDatas = diaryEntityData;
            }
        });
    }

    public LiveData<List<DiaryEntityData>> getDiaryEntityData() {
        return mDiary;
    }

    public void fetchDiary() {
        // 这里应该在Repository中完成
        TaskScheduler.execute(new Task<List<DiaryEntityData>>() {
            @Override
            public List<DiaryEntityData> doInBackground() throws InterruptedException {
                return CoreManager.getImpl(IDiaryRepositoryApi.class).searchAllDiary();
            }

            @Override
            public void onSuccess(List<DiaryEntityData> diaryEntityData) {
                parseDiaries(diaryEntityData);
            }
        });
    }

    @MainThread
    private void parseDiaries(List<DiaryEntityData> diaryEntityData) {
        List<DiaryEntityData> data = diaryEntityData;
        mDiaryDatas = diaryEntityData;
        mDiary.postValue(data);
    }

    public void insertTestData() {
        CoreManager.getImpl(IDiaryProvider.class).loadDiaryData();
    }
}
