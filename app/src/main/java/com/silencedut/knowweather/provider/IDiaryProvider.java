package com.silencedut.knowweather.provider;

import android.os.Handler;
import android.support.annotation.WorkerThread;

import com.silencedut.knowweather.ui.adapter.diary.DiaryEntityData;
import com.silencedut.weather_core.api.ICoreApi;

import java.util.List;

/**
 * 日历
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/13
 */

public interface IDiaryProvider extends ICoreApi {
    /**
     * 根据传入的日记时间获取数据
     *
     * @param date 日记时间
     * @return 当天的所有日记
     */
    @WorkerThread
    List<DiaryEntityData> searchDiary(String date);

    @WorkerThread
    List<DiaryEntityData> searchAllDiary();

    /**
     * @return
     */
    Handler getDiaryWorkHandler();

    /**
     * 保存日记
     *
     * @param entityData 当前填写的日记
     */
    void saveCurrentDiary(DiaryEntityData entityData);

    void loadDiaryData();
}
