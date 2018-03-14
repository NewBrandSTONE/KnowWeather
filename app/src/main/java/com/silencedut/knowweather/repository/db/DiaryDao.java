package com.silencedut.knowweather.repository.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.silencedut.knowweather.ui.adapter.diary.DiaryEntityData;

import java.util.List;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/13
 */
@Dao
public interface DiaryDao {
    @Query("SELECT * FROM diary")
    List<DiaryEntityData> getAll();

    @Insert
    void insertDiary(DiaryEntityData diary);
}
