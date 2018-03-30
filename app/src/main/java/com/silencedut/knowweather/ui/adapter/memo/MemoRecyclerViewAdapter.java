package com.silencedut.knowweather.ui.adapter.memo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silencedut.knowweather.R;

import java.util.List;

/**
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/30
 */

public class MemoRecyclerViewAdapter extends RecyclerView.Adapter<MemoRecyclerViewAdapter.ViewHolder> {

    private List<MemoEntityData> mDatas;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MemoRecyclerViewAdapter(Context context, List<MemoEntityData> datas) {
        this.mDatas = datas;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(mLayoutInflater.inflate(R.layout.memo_item_layout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.content.setText(mDatas.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public List<MemoEntityData> getmDatas() {
        return mDatas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;

        ViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.tv_content);
        }
    }

}
