package com.silencedut.knowweather.ui.adapter.diary;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.knowweather.R;

/**
 * 日记实体类
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/13
 */
@Entity(tableName = "diary")
public class DiaryEntityData implements BaseAdapterData {
    @PrimaryKey(autoGenerate = true)
    private int diaryId;
    /**
     * 类型-跑步；吃药
     */
    private String type;
    /**
     * 内容
     */
    private String content;
    /**
     * 记录时间
     */
    @ColumnInfo(name = "record_date")
    private String recordDate;

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(@NonNull int diaryId) {
        this.diaryId = diaryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public int getContentViewId() {
        return R.layout.weather_item_guide;
    }
}
