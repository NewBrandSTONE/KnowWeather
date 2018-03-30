package com.silencedut.knowweather.repository.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.silencedut.knowweather.ui.adapter.memo.MemoEntityData;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/30
 */
@Database(entities = {MemoEntityData.class}, version = 1)
public abstract class MemoDatabase extends RoomDatabase {
    public abstract MemoDao dao();
}
