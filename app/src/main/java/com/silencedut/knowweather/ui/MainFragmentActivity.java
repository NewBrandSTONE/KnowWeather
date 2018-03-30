package com.silencedut.knowweather.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.ui.fragment.alarm.HealthAlarmFragment;
import com.silencedut.knowweather.ui.fragment.diary.HealthDiaryFragment;
import com.silencedut.knowweather.ui.fragment.memo.HealthMemoFragment;
import com.silencedut.knowweather.ui.fragment.mine.HealthMineFragment;
import com.silencedut.knowweather.ui.fragment.weather.HealthWeatherFragment;
import com.silencedut.weather_core.corebase.BaseActivity;

import butterknife.BindView;

/**
 * 包含有底部导航栏的Activity
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/12
 */

public class MainFragmentActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_main_navigation)
    BottomNavigationView mBottomNavigationView;

    private HealthWeatherFragment mWeatherFragment;
    private HealthDiaryFragment mDiaryFragment;
    private HealthAlarmFragment mAlarmFragment;
    private HealthMineFragment mMineFragment;
    private HealthMemoFragment mMemoFragment;

    @Override
    public int getContentViewId() {
        return R.layout.main_fragment_activity;
    }

    @Override
    protected void initDataObserver() {
        // 初始化Fragment
        loadFragment();
    }

    private void loadFragment() {
        mWeatherFragment = new HealthWeatherFragment();
        mAlarmFragment = new HealthAlarmFragment();
        mDiaryFragment = new HealthDiaryFragment();
        mMineFragment = new HealthMineFragment();
        mMemoFragment = new HealthMemoFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_main, mWeatherFragment, mWeatherFragment.getClass().getSimpleName())
                .add(R.id.frame_main, mDiaryFragment, mDiaryFragment.getClass().getSimpleName())
                .add(R.id.frame_main, mAlarmFragment, mAlarmFragment.getClass().getSimpleName())
                .add(R.id.frame_main, mMineFragment, mMineFragment.getClass().getSimpleName())
                .add(R.id.frame_main, mMemoFragment, mMemoFragment.getClass().getSimpleName())
                .show(mWeatherFragment)
                .hide(mDiaryFragment)
                .hide(mAlarmFragment)
                .hide(mMineFragment)
                .hide(mMemoFragment)
                .commit();
    }

    @Override
    public void initViews() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.menu_weather_item: {
                transaction
                        .show(mWeatherFragment)
                        .hide(mDiaryFragment)
                        .hide(mAlarmFragment)
                        .hide(mMineFragment)
                        .hide(mMemoFragment)
                        .commit();

                break;
            }
            case R.id.menu_record_item: {
                transaction
                        .show(mDiaryFragment)
                        .hide(mAlarmFragment)
                        .hide(mWeatherFragment)
                        .hide(mMineFragment)
                        .hide(mMemoFragment)
                        .commit();
                break;
            }
            case R.id.menu_alarm_item: {
                transaction
                        .show(mAlarmFragment)
                        .hide(mDiaryFragment)
                        .hide(mWeatherFragment)
                        .hide(mMineFragment)
                        .hide(mMemoFragment)
                        .commit();
                break;
            }
            case R.id.menu_mine_item: {
                transaction
                        .show(mMineFragment)
                        .hide(mDiaryFragment)
                        .hide(mWeatherFragment)
                        .hide(mAlarmFragment)
                        .hide(mMemoFragment)
                        .commit();
                break;
            }
            case R.id.menu_memo_item: {
                transaction
                        .show(mMemoFragment)
                        .hide(mDiaryFragment)
                        .hide(mWeatherFragment)
                        .hide(mAlarmFragment)
                        .hide(mMineFragment)
                        .commit();
                break;
            }
            default: {
                break;
            }
        }
        return true;
    }
}
