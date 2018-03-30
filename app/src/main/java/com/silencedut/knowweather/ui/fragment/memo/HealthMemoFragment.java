package com.silencedut.knowweather.ui.fragment.memo;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.ui.adapter.memo.MemoEntityData;
import com.silencedut.knowweather.ui.adapter.memo.MemoRecyclerViewAdapter;
import com.silencedut.knowweather.ui.recyclerview.OzItemTouchHepler;
import com.silencedut.knowweather.viewmodel.memo.MemoViewModel;
import com.silencedut.weather_core.corebase.BaseFragment;
import com.silencedut.weather_core.viewmodel.ModelProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 备忘录
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/29
 */

public class HealthMemoFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView mList;
    private MemoViewModel mViewModel;
    private MemoRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void initDataObserver() {
        mViewModel = ModelProvider.getModel(this, MemoViewModel.class);

        mViewModel.getmDatas().observe(this, new Observer<List<MemoEntityData>>() {
            @Override
            public void onChanged(@Nullable List<MemoEntityData> memoEntityData) {
                // 清空adapter数据
                onMoreMemoData(memoEntityData);
                // 加载更多数据
            }
        });
        // 获取数据
        mViewModel.fetchMemoData();
    }

    private void onMoreMemoData(List<MemoEntityData> datas) {
        if (datas != null) {
            mRecyclerViewAdapter = new MemoRecyclerViewAdapter(getContext(), datas);
            mList.setAdapter(mRecyclerViewAdapter);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.memo_fragment_layout;
    }

    @Override
    public void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper.Callback itemTouchCallback = new OzItemTouchHepler(new OzItemTouchHepler.OnItemTouchCallbackListener() {
            @Override
            public void onSwiped(int adapterPosition) {
                List<MemoEntityData> diaryData = mRecyclerViewAdapter.getmDatas();
                final MemoEntityData data = diaryData.get(adapterPosition);
                // 提示确定要删除？
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("确定要删除这条数据吗？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mViewModel.fetchMemoData();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mViewModel.delete(data);
                        mViewModel.fetchMemoData();
                    }
                });
                builder.show();
            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                Log.i("asd", "srcPosition->" + srcPosition + " | targetPosition->" + targetPosition);
                return true;
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mList);
    }

    @OnClick(R.id.float_action)
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View alertDialogView = LayoutInflater.from(getContext()).inflate(R.layout.memo_alertdialog_layout, null);

        final TextInputEditText nameEt = alertDialogView.findViewById(R.id.ed_name);

        builder.setView(alertDialogView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 保存输入的内容
                mViewModel.insert(nameEt.getText().toString().trim());
                // 更新数据
                mViewModel.fetchMemoData();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
