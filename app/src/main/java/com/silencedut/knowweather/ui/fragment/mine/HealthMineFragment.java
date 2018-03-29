package com.silencedut.knowweather.ui.fragment.mine;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.ui.adapter.mine.MineEntityData;
import com.silencedut.knowweather.ui.adapter.mine.SexEnum;
import com.silencedut.knowweather.viewmodel.mine.MineViewModel;
import com.silencedut.weather_core.corebase.BaseFragment;
import com.silencedut.weather_core.viewmodel.ModelProvider;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人主页Fragment
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/28
 */

public class HealthMineFragment extends BaseFragment {

    private MineViewModel mMineViewModel;
    @BindView(R.id.tv_name)
    TextView mNameTv;
    @BindView(R.id.tv_age)
    TextView mAgeTv;
    @BindView(R.id.tv_sex)
    TextView mSexTv;
    @BindView(R.id.tv_address_content)
    TextView mAddressTv;
    @BindView(R.id.tv_job_content)
    TextView mJobTv;
    @BindView(R.id.tv_ill_content)
    TextView mIllTv;

    @Override
    public int getContentViewId() {
        return R.layout.mine_fragment_layout;
    }

    @Override
    public void initViews() {

    }

    @Override
    protected void initDataObserver() {
        mMineViewModel = ModelProvider.getModel(this, MineViewModel.class);
        mMineViewModel.getmMineDatas().observe(this, new Observer<MineEntityData>() {
            @Override
            public void onChanged(@Nullable MineEntityData mineEntityData) {
                onMoreData(mineEntityData);
            }
        });
        mMineViewModel.fetchMineData();
    }

    private void onMoreData(MineEntityData data) {
        // 加载数据
        if (data != null) {
            mNameTv.setText(data.getName() != null ? data.getName().substring(0, 1) : "");
            mAgeTv.setText(data.getAge() != null ? data.getAge() : "");
            mAddressTv.setText(data.getAddress() != null ? data.getAddress() : "");
            mJobTv.setText(data.getJob() != null ? data.getJob() : "");
            mIllTv.setText(data.getIll() != null ? data.getIll() : "");
            mSexTv.setText(SexEnum.MAN.name().equals(data.getSex()) ? "男" : "女");
        }
    }

    @OnClick(R.id.float_action)
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View alertDialogView = LayoutInflater.from(getContext()).inflate(R.layout.mine_setting_alertdialog_layout, null);

        final TextInputEditText nameEt = alertDialogView.findViewById(R.id.ed_name);
        final TextInputEditText ageEt = alertDialogView.findViewById(R.id.ed_age);
        final TextInputEditText addressEt = alertDialogView.findViewById(R.id.ed_address);
        final TextInputEditText jobEt = alertDialogView.findViewById(R.id.ed_job);
        final TextInputEditText illEt = alertDialogView.findViewById(R.id.ed_ill);
        final RadioButton manBtn = alertDialogView.findViewById(R.id.cb_man);

        builder.setView(alertDialogView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 保存用户输入的数据
                String sex = SexEnum.MAN.name();
                if (manBtn.isChecked()) {
                    sex = SexEnum.MAN.name();
                } else {
                    sex = SexEnum.WOMAN.name();
                }
                mMineViewModel.insert(nameEt.getText().toString().trim(), sex
                        , ageEt.getText().toString().trim(), addressEt.getText().toString().trim()
                        , jobEt.getText().toString().trim(), illEt.getText().toString().trim());
                // 更新数据
                mMineViewModel.fetchMineData();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
