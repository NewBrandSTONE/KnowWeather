package com.silencedut.knowweather.repository;

import android.os.Handler;

import com.silencedut.hub_annotation.HubInject;
import com.silencedut.knowweather.repository.db.MineDatabase;
import com.silencedut.knowweather.ui.adapter.mine.MineEntityData;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.repository.DBHelper;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/29
 */
@HubInject(api = IMineRepositoryApi.class)
public class MineRepositoryImpl implements IMineRepositoryApi {
    private static final String TAG = "MineRepositoryImpl";
    private static final String MINE_DB_NAME = "mine";
    private MineDatabase mMineDatabase;
    private Handler mHandler;

    @Override
    public MineEntityData fetchMineInfo() {
        return mMineDatabase.dao().queryLastOne();
    }

    @Override
    public void insert(final MineEntityData data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMineDatabase.dao().insert(data);
            }
        });
    }

    @Override
    public void update(MineEntityData data) {

    }

    @Override
    public void onCreate() {
        mMineDatabase = DBHelper.provider(MineDatabase.class, MINE_DB_NAME);
        mHandler = TaskScheduler.provideHandler(TAG);
    }
}
