package com.silencedut.knowweather.ui.adapter.diary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silencedut.knowweather.R;

import java.util.List;

/**
 * <一句话简述功能>
 * <功能详细描述>
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/14
 */

public class DiaryRecyclerViewAdapter extends RecyclerView.Adapter<DiaryRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<DiaryEntityData> mDatas;

    public DiaryRecyclerViewAdapter(Context context, List<DiaryEntityData> datas) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.diary_recycler_view_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mDatas.get(position).getType());
        holder.content.setText(mDatas.get(position).getContent());
        holder.unit.setText(mDatas.get(position).getType().equals("1") ? "小时" : "数量");
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        TextView unit;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_title);
            content = view.findViewById(R.id.tv_content);
            unit = view.findViewById(R.id.tv_unit);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ViewHolder", "onClick--> position = " + getPosition());
                }
            });
        }
    }
}
