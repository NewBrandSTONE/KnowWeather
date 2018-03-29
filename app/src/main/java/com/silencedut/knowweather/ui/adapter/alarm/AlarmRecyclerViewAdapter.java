package com.silencedut.knowweather.ui.adapter.alarm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silencedut.knowweather.R;

import java.util.List;

/**
 * 闹钟RecyclerView适配器
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/16
 */

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmViewHolder> {

    private Context mContext;
    private List<AlarmEntityData> mDatas;
    private LayoutInflater mLayoutInflater;

    public AlarmRecyclerViewAdapter(Context context, List<AlarmEntityData> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AlarmViewHolder viewHolder = new AlarmViewHolder(mLayoutInflater.inflate(R.layout.alarm_recycler_view_item_layout, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        holder.time.setText(mDatas.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView time;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
        }
    }
}
