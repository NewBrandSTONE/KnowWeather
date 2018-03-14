package com.silencedut.knowweather.ui.adapter.diary;

import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.knowweather.R;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/14
 */

public class DailyDiaryData implements BaseAdapterData {

    private DiaryEntityData diaryEntityData;

    public DiaryEntityData getDiaryEntityData() {
        return diaryEntityData;
    }

    @Override
    public int getContentViewId() {
        return R.layout.weather_item_guide;
    }
}
