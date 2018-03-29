package com.silencedut.knowweather.ui.fragment.diary;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
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
import com.silencedut.knowweather.ui.adapter.diary.DiaryTypeEnum;
import com.silencedut.knowweather.ui.calendar.CalendarCustomView;
import com.silencedut.knowweather.ui.calendar.ThemeDayView;
import com.silencedut.knowweather.ui.recyclerview.OzItemTouchHepler;
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
    private DiaryRecyclerViewAdapter mDiaryRecyclerViewAdapter;

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
                mDiaryModel.fetchDiary(date.toString());
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
        ItemTouchHelper.Callback itemTouchCallback = new OzItemTouchHepler(new OzItemTouchHepler.OnItemTouchCallbackListener() {
            @Override
            public void onSwiped(int adapterPosition) {
                List<DiaryEntityData> diaryData = mDiaryRecyclerViewAdapter.getDiaryData();
                final DiaryEntityData data = diaryData.get(adapterPosition);
                Log.i("asd", "position->" + adapterPosition);
                // 提示确定要删除？
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("确定要删除这条数据吗？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDiaryModel.fetchDiary(currentDate.toString());
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDiaryModel.deleteDiaryData(data);
                        mDiaryModel.fetchDiary(currentDate.toString());
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
        itemTouchHelper.attachToRecyclerView(rvToDoList);
    }

    @OnClick(R.id.float_action)
    public void onClick() {
        // 弹出窗口做类型判断
        // 添加数据
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.running_alertdialog_content_layout, null, false);
        final TextInputEditText editTime = dialogView.findViewById(R.id.ed_time);
        final TextInputEditText editeMethod = dialogView.findViewById(R.id.ed_method);
        final TextInputLayout textMethod = dialogView.findViewById(R.id.text_method);
        final TextInputLayout textTime = dialogView.findViewById(R.id.text_time);
        final AppCompatRadioButton sportTypeBtn = dialogView.findViewById(R.id.button_sport);
        final AppCompatRadioButton medicineTypeBtn = dialogView.findViewById(R.id.button_medicine);
        final AppCompatRadioButton heartRateBtn = dialogView.findViewById(R.id.button_heart_rate);
        final AppCompatRadioButton bloodPressureBtn = dialogView.findViewById(R.id.button_blood_pressure);

        heartRateBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    clearSetHintAndText(textMethod, textTime, "方式", "静止心率", "bpm", "");
                }
            }
        });

        bloodPressureBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    clearSetHintAndText(textMethod, textTime, "方式", "主动脉血压", "mmHg", "");
                }
            }
        });

        sportTypeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    clearSetHintAndText(textMethod, textTime, "方式", "", "时长", "");
                }
            }
        });

        medicineTypeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    clearSetHintAndText(textMethod, textTime, "药品名称", "", "数量", "");
                }
            }
        });


        builder.setView(dialogView);
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 保存数量
                String sportsTime = editTime.getText().toString().trim();
                // 保存药品名称或者运动种类
                String sportMethod = editeMethod.getText().toString().trim();
                // 保存类型, 1-运动；2-吃药；3-心率；4-血压
                String type = getSelectType(sportTypeBtn.isChecked(), medicineTypeBtn.isChecked()
                        , heartRateBtn.isChecked(), bloodPressureBtn.isChecked());
                mDiaryModel.insertSportData(sportMethod, sportsTime, currentDate.toString(), type);
                mDiaryModel.fetchDiary(currentDate.toString());
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void clearSetHintAndText(TextInputLayout methodInputLayout, TextInputLayout timeInputLayout, String methodHint
            , String methodText, String timeHint, String timeText) {
        // 设置方法
        methodInputLayout.setHint(methodHint);
        methodInputLayout.getEditText().setText(methodText);
        // 设置数量
        timeInputLayout.setHint(timeHint);
        timeInputLayout.getEditText().setText(timeText);
    }

    private String getSelectType(boolean isSport, boolean isMedicine
            , boolean isHeartRate, boolean isBloodPressure) {
        if (isSport) {
            return DiaryTypeEnum.SPORT.name();
        } else if (isMedicine) {
            return DiaryTypeEnum.MEDICINE.name();
        } else if (isHeartRate) {
            return DiaryTypeEnum.HEART_RATE.name();
        } else if (isBloodPressure) {
            return DiaryTypeEnum.BLOOD_PRESSURE.name();
        }
        return "";
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
        mDiaryModel.fetchDiary(currentDate.toString());
    }

    /**
     * 当加载日记数据的时候
     *
     * @param diaryData 日记数据
     */
    private void onMoreDiaryData(List<DiaryEntityData> diaryData) {
        if (diaryData != null) {
            // 加载数据
            mDiaryRecyclerViewAdapter = new DiaryRecyclerViewAdapter(getContext(), diaryData);
            rvToDoList.setAdapter(mDiaryRecyclerViewAdapter);
        }
    }
}
