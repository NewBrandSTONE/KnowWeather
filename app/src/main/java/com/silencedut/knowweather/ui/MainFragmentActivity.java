package com.silencedut.knowweather.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.ui.fragment.diary.HealthDiaryFragment;
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
        mDiaryFragment = new HealthDiaryFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_main, mWeatherFragment, mWeatherFragment.getClass().getSimpleName())
                .add(R.id.frame_main, mDiaryFragment, mDiaryFragment.getClass().getSimpleName())
                .show(mWeatherFragment)
                .hide(mDiaryFragment)
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
                        .hide(mDiaryFragment).commit();
                break;
            }
            case R.id.menu_record_item: {
                transaction
                        .show(mDiaryFragment)
                        .hide(mWeatherFragment).commit();
                break;
            }
            case R.id.menu_alarm_item: {
                break;
            }
            default: {
                break;
            }
        }
        return true;
    }
}
