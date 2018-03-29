package com.silencedut.knowweather.repository.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.silencedut.knowweather.ui.adapter.alarm.AlarmEntityData;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/18
 */
@Database(entities = {AlarmEntityData.class}, version = 1)
public abstract class AlarmDatabase extends RoomDatabase {
    public abstract AlarmDao dao();
}
