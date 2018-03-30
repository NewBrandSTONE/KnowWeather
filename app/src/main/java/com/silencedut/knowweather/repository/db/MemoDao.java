package com.silencedut.knowweather.repository.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.silencedut.knowweather.ui.adapter.memo.MemoEntityData;

import java.util.List;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/30
 */
@Dao
public interface MemoDao {

    @Query("SELECT * FROM memo")
    List<MemoEntityData> queryAll();

    @Insert
    void insert(MemoEntityData data);

    @Delete
    void delete(MemoEntityData data);
}
