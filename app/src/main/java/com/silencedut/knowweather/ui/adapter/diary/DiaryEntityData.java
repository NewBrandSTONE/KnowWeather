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
     * 可以当做运动方式：跑步/游动
     * 或者
     * 药品名称：阿莫西林/幸福感冒
     */
    private String method;
    /**
     * 内容
     */
    private String content;
    /**
     * 类型 1-跑步；2-吃药
     */
    private String typeId;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public int getContentViewId() {
        return R.layout.weather_item_guide;
    }
}
