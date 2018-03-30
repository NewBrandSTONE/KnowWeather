package com.silencedut.knowweather.ui.adapter.memo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/30
 */
@Entity(tableName = "memo")
public class MemoEntityData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;

    public MemoEntityData() {

    }

    @Ignore
    public MemoEntityData(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MemoEntityData{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
