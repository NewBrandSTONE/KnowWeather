package com.silencedut.knowweather.repository;

import android.os.Handler;

import com.silencedut.hub_annotation.HubInject;
import com.silencedut.knowweather.repository.db.MemoDatabase;
import com.silencedut.knowweather.ui.adapter.memo.MemoEntityData;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.repository.DBHelper;

import java.util.List;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/30
 */
@HubInject(api = IMemoRepositoryApi.class)
public class MemoRepositoryImpl implements IMemoRepositoryApi {

    private static final String TAG = "MemoRepositoryImpl";
    private static final String MEMO_DB_NAME = "memo";
    private MemoDatabase mMineDatabase;
    private Handler mHandler;

    @Override
    public List<MemoEntityData> fetchAll() {
        return mMineDatabase.dao().queryAll();
    }

    @Override
    public void insert(final MemoEntityData data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMineDatabase.dao().insert(data);
            }
        });
    }

    @Override
    public void delete(final MemoEntityData data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMineDatabase.dao().delete(data);
            }
        });
    }

    @Override
    public void onCreate() {
        mMineDatabase = DBHelper.provider(MemoDatabase.class, MEMO_DB_NAME);
        mHandler = TaskScheduler.provideHandler(TAG);
    }
}
