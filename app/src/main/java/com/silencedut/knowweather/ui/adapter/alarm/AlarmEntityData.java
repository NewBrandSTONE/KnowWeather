package com.silencedut.knowweather.ui.adapter.alarm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;

/**
 * 闹钟实体类
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/16
 */
@Entity(tableName = "alarm")
public class AlarmEntityData implements BaseAdapterData {
    @PrimaryKey(autoGenerate = true)
    private int alarmId;
    private String time;
    /**
     * 0-停用；1-启用
     */
    private int enable;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Ignore
    public AlarmEntityData(int alarmId, String time, int enable) {
        this.alarmId = alarmId;
        this.time = time;
        this.enable = enable;
    }

    public AlarmEntityData() {

    }
}
