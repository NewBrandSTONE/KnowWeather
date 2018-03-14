package com.silencedut.knowweather.ui.adapter.diary;

import android.view.View;

import com.silencedut.baselib.commonhelper.adapter.BaseRecyclerAdapter;
import com.silencedut.baselib.commonhelper.adapter.BaseViewHolder;

/**
 * 记录模块下的ViewHolder
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/13
 */

public class DiaryRecyclerViewHolder extends BaseViewHolder<DiaryData> {
    public DiaryRecyclerViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public void updateItem(DiaryData data, int position) {

    }
}
