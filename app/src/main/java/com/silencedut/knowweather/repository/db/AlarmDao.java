package com.silencedut.knowweather.repository.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.silencedut.knowweather.ui.adapter.alarm.AlarmEntityData;

import java.util.List;

/**
 * 闹钟数据DAO
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/16
 */
@Dao
public interface AlarmDao {
    @Query("SELECT * FROM alarm")
    List<AlarmEntityData> getAll();

    /**
     * 插入数据
     *
     * @param data 闹钟数据
     */
    @Insert
    long insertDiary(AlarmEntityData data);

    @Delete
    void delete(AlarmEntityData data);

    @Update
    void update(AlarmEntityData data);
}
