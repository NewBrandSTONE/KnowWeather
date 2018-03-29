package com.silencedut.knowweather.repository.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.silencedut.knowweather.ui.adapter.mine.MineEntityData;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/29
 */
@Database(entities = {MineEntityData.class}, version = 1)
public abstract class MineDatabase extends RoomDatabase {
    public abstract MineDao dao();
}
