package com.silencedut.knowweather.repository.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
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

    /**
     * 插入数据
     *
     * @param diary 日记
     */
    @Insert
    void insertDiary(DiaryEntityData diary);

    @Query("SELECT * FROM diary WHERE record_date = :date")
    List<DiaryEntityData> queryDiaryByDate(String date);

    @Delete
    void delete(DiaryEntityData data);
}
