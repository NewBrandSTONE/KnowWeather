package com.silencedut.knowweather.ui.fragment.mine;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.silencedut.knowweather.R;
import com.silencedut.weather_core.corebase.BaseFragment;

import butterknife.OnClick;

/**
 * 个人主页Fragment
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/28
 */

public class HealthMineFragment extends BaseFragment {


    @Override
    public int getContentViewId() {
        return R.layout.mine_fragment_layout;
    }

    @Override
    public void initViews() {

    }

    @OnClick(R.id.float_action)
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View alertDialogView = LayoutInflater.from(getContext()).inflate(R.layout.mine_setting_alertdialog_layout, null);
        builder.setView(alertDialogView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 保存用户输入的数据
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
