package com.silencedut.knowweather.ui.fragment.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.ui.adapter.alarm.AlarmEntityData;
import com.silencedut.knowweather.ui.adapter.alarm.AlarmRecyclerViewAdapter;
import com.silencedut.knowweather.viewmodel.alarm.AlarmViewModel;
import com.silencedut.weather_core.corebase.BaseFragment;
import com.silencedut.weather_core.viewmodel.ModelProvider;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 闹钟界面
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/13
 */

public class HealthAlarmFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView rvToDoList;

    private AlarmViewModel mViewModel;
    private AlarmRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.alarm_fragment_layout;
    }

    @Override
    public void initViews() {
        initeAlarmRecyclerView();
    }

    private void initeAlarmRecyclerView() {
        rvToDoList.setHasFixedSize(true);
        rvToDoList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initDataObserver() {
        // 获取闹钟数据
        mViewModel = ModelProvider.getModel(this, AlarmViewModel.class);
        mViewModel.getmAlarm().observe(this, new Observer<List<AlarmEntityData>>() {
            @Override
            public void onChanged(@Nullable List<AlarmEntityData> alarmEntityData) {
                // 加载数据
                onMoreAlarmData(alarmEntityData);
            }
        });
        mViewModel.fetchAlarmData();
    }

    /**
     * 加载数据
     *
     * @param datas 闹钟数据
     */
    private void onMoreAlarmData(List<AlarmEntityData> datas) {
        if (datas != null) {
            // 加载数据
            mRecyclerViewAdapter = new AlarmRecyclerViewAdapter(getContext(), datas);
            rvToDoList.setAdapter(mRecyclerViewAdapter);
        }
    }

    @OnClick(R.id.float_action)
    public void onClick() {
        // 添加闹钟
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.weather_edit_alarm_layout, null);
        final TimePicker timePicker = rootView.findViewById(R.id.time);
        builder.setView(rootView);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int hour;
                int minus;

                hour = timePicker.getCurrentHour();
                minus = timePicker.getCurrentMinute();

                // 保存数据
                try {
                    mViewModel.insertData(String.format("%02d", hour) + ":" + String.format("%02d", minus), 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 设置闹钟
                initAlarm(hour, minus);
                mViewModel.fetchAlarmData();
            }
        });
        builder.show();
    }

    private void initAlarm(int hours, int minus) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minus);
        calendar.set(Calendar.SECOND, 0);

        //创建Intent对象，action为ELITOR_CLOCK，附加信息为字符串“你该打酱油了”
        Intent intent = new Intent("ELITOR_CLOCK");
        intent.putExtra("msg", "闹钟响了");
        //定义一个PendingIntent对象，PendingIntent.getBroadcast包含了sendBroadcast的动作。
        //也就是发送了action 为"ELITOR_CLOCK"的intent
        PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

        //AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
//        AlarmManager am = getActivity.(AlarmManager)getSystemService(ALARM_SERVICE);
        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        //设置闹钟从当前时间开始，每隔5s执行一次PendingIntent对象pi，注意第一个参数与第二个参数的关系
        // 5秒后通过PendingIntent pi对象发送广播
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5 * 1000, pi);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }
}
