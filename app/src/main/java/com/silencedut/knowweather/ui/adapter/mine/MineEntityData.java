package com.silencedut.knowweather.ui.adapter.mine;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;

/**
 * 【我的】模块实体类
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/29
 */
@Entity(tableName = "mine")
public class MineEntityData implements BaseAdapterData {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String age;
    private String sex;
    private String job;
    private String address;
    private String ill;

    public MineEntityData() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIll() {
        return ill;
    }

    public void setIll(String ill) {
        this.ill = ill;
    }

    @Ignore
    public MineEntityData(int id, String name, String age, String sex, String job, String address, String ill) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.job = job;
        this.address = address;
        this.ill = ill;
    }

    @Override
    public int getContentViewId() {
        return 0;
    }
}
