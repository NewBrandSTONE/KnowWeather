package com.silencedut.knowweather.repository.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.silencedut.knowweather.ui.adapter.mine.MineEntityData;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/29
 */
@Dao
public interface MineDao {
    @Query("SELECT * FROM mine WHERE id = (SELECT MAX(id) FROM mine)")
    MineEntityData queryLastOne();

    @Insert
    void insert(MineEntityData data);
}
