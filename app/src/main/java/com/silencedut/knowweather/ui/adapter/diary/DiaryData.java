package com.silencedut.knowweather.ui.adapter.diary;

import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.knowweather.R;

/**
 * 日历数据
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/13
 */

public class DiaryData implements BaseAdapterData {
    @Override
    public int getContentViewId() {
        return R.layout.weather_item_hour_forecast;
    }
}
