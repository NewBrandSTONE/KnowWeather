package com.silencedut.knowweather.viewmodel.mine;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;

import com.silencedut.knowweather.repository.IMineRepositoryApi;
import com.silencedut.knowweather.ui.adapter.mine.MineEntityData;
import com.silencedut.taskscheduler.Task;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.viewmodel.BaseViewModel;

/**
 * 个人设置ViewModel
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/29
 */

public class MineViewModel extends BaseViewModel {

    private MutableLiveData<MineEntityData> mMineDatas;

    @Override
    protected void onCreate() {
        mMineDatas = new MutableLiveData<>();
    }

    public void fetchMineData() {
        TaskScheduler.execute(new Task<MineEntityData>() {
            @Override
            public MineEntityData doInBackground() throws InterruptedException {
                return CoreManager.getImpl(IMineRepositoryApi.class).fetchMineInfo();
            }

            @Override
            public void onSuccess(MineEntityData datas) {
                parseData(datas);
            }
        });
    }

    @MainThread
    private void parseData(MineEntityData datas) {
        mMineDatas.postValue(datas);
    }

    public MutableLiveData<MineEntityData> getmMineDatas() {
        return mMineDatas;
    }

    public void insert(String name, String sex, String age
            , String address, String job, String ill) {
        MineEntityData data = new MineEntityData(0, name, age, sex, job, address, ill);
        CoreManager.getImpl(IMineRepositoryApi.class).insert(data);
    }

}
