package com.silencedut.knowweather.viewmodel.memo;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;

import com.silencedut.knowweather.repository.IMemoRepositoryApi;
import com.silencedut.knowweather.ui.adapter.memo.MemoEntityData;
import com.silencedut.taskscheduler.Task;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.viewmodel.BaseViewModel;

import java.util.List;

/**
 * 备忘录
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/30
 */

public class MemoViewModel extends BaseViewModel {

    private MutableLiveData<List<MemoEntityData>> mDatas;

    @Override
    protected void onCreate() {
        mDatas = new MutableLiveData<>();
    }

    @MainThread
    private void parseData(List<MemoEntityData> datas) {
        mDatas.postValue(datas);
    }

    public void fetchMemoData() {
        TaskScheduler.execute(new Task<List<MemoEntityData>>() {
            @Override
            public List<MemoEntityData> doInBackground() throws InterruptedException {
                return CoreManager.getImpl(IMemoRepositoryApi.class).fetchAll();
            }

            @Override
            public void onSuccess(List<MemoEntityData> datas) {
                parseData(datas);
            }
        });
    }

    public void insert(String content) {
        MemoEntityData data = new MemoEntityData();
        data.setContent(content);
        CoreManager.getImpl(IMemoRepositoryApi.class).insert(data);
    }

    public void delete(MemoEntityData data) {
        CoreManager.getImpl(IMemoRepositoryApi.class).delete(data);
    }

    public MutableLiveData<List<MemoEntityData>> getmDatas() {
        return mDatas;
    }
}
