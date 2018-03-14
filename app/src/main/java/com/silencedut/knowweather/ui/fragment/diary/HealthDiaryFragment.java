package com.silencedut.knowweather.ui.fragment.diary;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.silencedut.baselib.commonhelper.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.ui.adapter.diary.DiaryEntityData;
import com.silencedut.knowweather.ui.adapter.diary.DiaryRecyclerViewAdapter;
import com.silencedut.knowweather.ui.calendar.CalendarCustomView;
import com.silencedut.knowweather.ui.calendar.ThemeDayView;
import com.silencedut.knowweather.viewmodel.diary.DiaryViewModel;
import com.silencedut.weather_core.corebase.BaseFragment;
import com.silencedut.weather_core.viewmodel.ModelProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 日记Fragment
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/12
 */

public class HealthDiaryFragment extends BaseFragment {
    private static final String TAG = HealthDiaryFragment.class.getSimpleName();
    @BindView(R.id.show_year_view)
    TextView tvYear;
    @BindView(R.id.show_month_view)
    TextView tvMonth;
    @BindView(R.id.back_today_button)
    TextView backToday;
    @BindView(R.id.content)
    CoordinatorLayout content;
    @BindView(R.id.calendar_view)
    MonthPager monthPager;
    @BindView(R.id.list)
    RecyclerView rvToDoList;
    @BindView(R.id.scroll_switch)
    TextView scrollSwitch;
    @BindView(R.id.theme_switch)
    TextView themeSwitch;
    @BindView(R.id.next_month)
    TextView nextMonthBtn;
    @BindView(R.id.last_month)
    TextView lastMonthBtn;

    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private CalendarDate currentDate;
    private boolean initiated = false;
    private DiaryViewModel mDiaryModel;
    private BaseRecyclerAdapter mMoreInfoAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.diary_fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
        calendarAdapter.switchToWeek(monthPager.getRowIndex());
    }

    @Override
    public void initViews() {
        monthPager.setViewHeight(Utils.dpi2px(getContext(), 270));
        initTodoListRecyclerView();
        initCurrentDate();
        initCalendarView();
        initToolbarClickListener();
    }

    private void initToolbarClickListener() {
        backToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBackToDayBtn();
            }
        });
        scrollSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK) {
                    Utils.scrollTo(content, rvToDoList, monthPager.getViewHeight(), 200);
                    calendarAdapter.switchToMonth();
                } else {
                    Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
                    calendarAdapter.switchToWeek(monthPager.getRowIndex());
                }
            }
        });
        themeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshSelectBackground();
            }
        });

        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);
            }
        });
        lastMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);
            }
        });
    }

    private void refreshSelectBackground() {
        ThemeDayView themeDayView = new ThemeDayView(getContext(), R.layout.calendar_custom_day_focus);
        calendarAdapter.setCustomDayRenderer(themeDayView);
        calendarAdapter.notifyDataSetChanged();
        calendarAdapter.notifyDataChanged(new CalendarDate());
    }

    private void onClickBackToDayBtn() {
        refreshMonthPager();
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
        tvYear.setText(today.getYear() + "年");
        tvMonth.setText(today.getMonth() + "");
    }

    private void initCalendarView() {
        initListener();
        CalendarCustomView customDayView = new CalendarCustomView(getContext(), R.layout.calendar_custom_day);
        calendarAdapter = new CalendarViewAdapter(
                getContext(),
                onSelectDateListener,
                CalendarAttr.CalendarType.WEEK,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
        calendarAdapter.setOnCalendarTypeChangedListener(new CalendarViewAdapter.OnCalendarTypeChanged() {
            @Override
            public void onCalendarTypeChanged(CalendarAttr.CalendarType type) {
                rvToDoList.scrollToPosition(0);
            }
        });
        initMarkData();
        initMonthPager();
    }

    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    tvYear.setText(date.getYear() + "年");
                    tvMonth.setText(date.getMonth() + "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initMarkData() {
        HashMap<String, String> markData = new HashMap<>();
        markData.put("2017-8-9", "1");
        markData.put("2017-7-9", "0");
        markData.put("2017-6-9", "1");
        markData.put("2017-6-10", "0");
        calendarAdapter.setMarkData(markData);
    }

    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                monthPager.selectOtherMonth(offset);
            }
        };
    }

    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        tvYear.setText(date.getYear() + "年");
        tvMonth.setText(date.getMonth() + "");
    }

    private void initCurrentDate() {
        currentDate = new CalendarDate();
        tvYear.setText(currentDate.getYear() + "年");
        tvMonth.setText(currentDate.getMonth() + "");
    }

    /**
     * 初始化代办任务列表
     */
    private void initTodoListRecyclerView() {
        rvToDoList.setHasFixedSize(true);
        rvToDoList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvToDoList.setAdapter(new BaseRecyclerAdapter(getContext()));
        mMoreInfoAdapter = new BaseRecyclerAdapter(getContext());
    }

    @OnClick(R.id.float_action)
    public void onClick() {
        // 弹出窗口做类型判断
        // 添加数据
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("asd");
        builder.setMessage("asd2");
        builder.show();
    }

    @Override
    protected void initDataObserver() {
        mDiaryModel = ModelProvider.getModel(this, DiaryViewModel.class);

        mDiaryModel.getDiaryEntityData().observe(this, new Observer<List<DiaryEntityData>>() {
            @Override
            public void onChanged(@Nullable List<DiaryEntityData> diaryEntityData) {
                mMoreInfoAdapter.clear();
                onMoreDiaryData(diaryEntityData);
            }
        });
        mDiaryModel.fetchDiary();
    }

    /**
     * 当加载日记数据的时候
     *
     * @param diaryData 日记数据
     */
    private void onMoreDiaryData(List<DiaryEntityData> diaryData) {
        if (diaryData != null) {
//            mMoreInfoAdapter.registerHolder(DiaryRecyclerViewHolder.class, diaryData);
            // 加载数据
            rvToDoList.setAdapter(new DiaryRecyclerViewAdapter(getContext(), diaryData));
        }
    }
}
