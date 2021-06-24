package org.mylibrary.librarycalendar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import org.mylibrary.librarycalendar.helper.PreferenceHelper;
import org.mylibrary.librarycalendar.helper.TimeUtil;
import org.mylibrary.librarycalendar.listener.CalenderDayClickListener;
import org.mylibrary.librarycalendar.model.DayContainerModel;
import org.mylibrary.librarycalendar.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalenderEvent extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "CalenderEvent";

    private LinearLayout weekOneLayout;
    private LinearLayout weekTwoLayout;
    private LinearLayout weekThreeLayout;
    private LinearLayout weekFourLayout;
    private LinearLayout weekFiveLayout;
    private LinearLayout weekSixLayout;

    private static final String[] MONTH_NAMES = {"January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"};

    private LinearLayout[] weeks;
    private TextView[] days;
    private LinearLayout[] daysContainer;
    private TextView[] eventsTextViewList;

    private static final String TEXT_EVENT = " TEXT";
    private static final String COLOR_EVENT = " COLOR";

    private Button buttonPrevious, buttonNext;

    private TextView textViewMonthName;

    private Calendar mCalendar;

    private static final String CUSTOM_GREY = "#a0a0a0";

    private Context mContext;

    private List<DayContainerModel> dayContainerList;

    private String mToday;

    //selected color element
    private TextView mSelectedTexView;
    private LinearLayout mSelectedLinearLayout;
    private int mPreviousColor;


    private PreferenceHelper preferenceHelper;

    private CalenderDayClickListener mCalenderDayClickListener;

    // View component
    private View mainView;

    // View setter component;
    private int mBackgroundColor;
    private int mSelectorColor;
    private int mSelectedDayTextColor;
    private int mCurrentMonthDayColor;
    private int mOffMonthDayColor;
    private int mMonthTextColor;
    private int mWeekNameColor;

    private Drawable nextButtonDrawable;
    private Drawable prevButtonDrawable;

    // Default component
    private static final int DEFAULT_BACKGROUND_COLOR =  Color.parseColor("#414249");
    private static final int DEFAULT_SELECTED_DAY_COLOR = Color.WHITE;
    private static final int DEFAULT_CURRENT_MONTH_DAY_COLOR = Color.BLACK;
    private static final int DEFAULT_OFF_MONTH_DAY_COLOR = Color.WHITE;
    private static final int DEFAULT_SELECTOR_COLOR = Color.WHITE;//orange
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#1E53C5");//blue


    public CalenderEvent(Context context) {
        super(context);
    }

    public CalenderEvent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        initAttrs(attrs);

        initView(context);

        preferenceHelper = PreferenceHelper.init(context);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        weekOneLayout = findViewById(R.id.calendar_week_1);
        weekTwoLayout = findViewById(R.id.calendar_week_2);
        weekThreeLayout = findViewById(R.id.calendar_week_3);
        weekFourLayout = findViewById(R.id.calendar_week_4);
        weekFiveLayout = findViewById(R.id.calendar_week_5);
        weekSixLayout = findViewById(R.id.calendar_week_6);

        buttonNext = findViewById(R.id.button_next);
        buttonPrevious = findViewById(R.id.button_previous);

        textViewMonthName = findViewById(R.id.text_view_month_name);

        // week container
        LinearLayout linearLayoutWeak = findViewById(R.id.linear_layout_week);

        weeks = new LinearLayout[6];
        days = new TextView[6 * 7];
        eventsTextViewList = new TextView[6 * 7];
        daysContainer = new LinearLayout[6 * 7];

        weeks[0] = weekOneLayout;
        weeks[1] = weekTwoLayout;
        weeks[2] = weekThreeLayout;
        weeks[3] = weekFourLayout;
        weeks[4] = weekFiveLayout;
        weeks[5] = weekSixLayout;

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        mCalendar = Calendar.getInstance();
        mToday = mCalendar.get(Calendar.DATE) + " " + MONTH_NAMES[mCalendar.get(Calendar.MONTH)] + " " + mCalendar.get(Calendar.YEAR);

        Log.d(TAG, "Today, " + mToday);

        initDaysInCalender(getdaysLayoutParams(), mContext, metrics);

        initCalender(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH));

        buttonPrevious.setOnClickListener(this);
        buttonNext.setOnClickListener(this);

        // set view element
        mainView.setBackgroundColor(mBackgroundColor);

        textViewMonthName.setTextColor(mMonthTextColor);

        for (int i = 0; i < linearLayoutWeak.getChildCount(); i++) {
            TextView textViewWeek = (TextView) linearLayoutWeak.getChildAt(i);
            textViewWeek.setTextColor(mWeekNameColor);
        }

        if (nextButtonDrawable != null) {
            buttonNext.setBackground(nextButtonDrawable);
        }
        if (prevButtonDrawable != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                buttonPrevious.setBackground(prevButtonDrawable);
            }
            int t = Color.parseColor("#ffffff");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                buttonPrevious.setBackgroundTintList(ColorStateList.valueOf(t));
            }
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_next) {
            gotoNextMonth();
        } else if (view.getId() == R.id.button_previous) {
            gotoPreviousMonth();
        }
    }


    private void initCalender(int selectedYear, int selectedMonth) {

        dayContainerList = new ArrayList<>();

        mCalendar.set(selectedYear, selectedMonth, 1);

        textViewMonthName.setText(MONTH_NAMES[selectedMonth] + ", " + selectedYear);

        int daysInCurrentMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);


        int index = 0;

        int firstDayOfCurrentMonth = mCalendar.get(Calendar.DAY_OF_WEEK);

        int previousLeftMonthDays = firstDayOfCurrentMonth - 1;

        int nextMonthDays = 42 - (daysInCurrentMonth + previousLeftMonthDays);

        // now set previous date

        if (previousLeftMonthDays != 0) {
            int prevMonth;
            int prevYear;
            if (selectedMonth > 0) {
                mCalendar.set(selectedYear, selectedMonth - 1, 1);
                prevMonth = selectedMonth - 1;
                prevYear = selectedYear;
            } else {
                mCalendar.set(selectedYear - 1, 11, 1);
                prevMonth = 11;
                prevYear = selectedYear - 1;
            }

            int previousMonthTotalDays = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            mCalendar.set(prevYear, prevMonth, (previousMonthTotalDays - previousLeftMonthDays));
            int prevCurrentDay = mCalendar.get(Calendar.DATE);
            prevCurrentDay++;
            for (int i = 0; i < previousLeftMonthDays; i++) {
                days[index].setText(String.valueOf(prevCurrentDay));
                days[index].setTextColor(mOffMonthDayColor);

                final DayContainerModel model = new DayContainerModel();
                model.setIndex(index);
                model.setYear(prevYear);
                model.setMonthNumber(prevMonth);
                model.setMonth(MONTH_NAMES[prevMonth]);
                model.setDay(prevCurrentDay);
                String date = model.getDay() + " " + model.getMonth() + " " + model.getYear();
                model.setDate(date);
                model.setTimeInMillisecond(TimeUtil.getTimestamp(date));

                Event event = getEvent(model.getTimeInMillisecond());
                model.setEvent(event);
                model.setHaveEvent(event != null);

                if (model.isHaveEvent()) {
                    eventsTextViewList[index].setText(event.getEventText());
                    eventsTextViewList[index].setVisibility(VISIBLE);
                    eventsTextViewList[index].setTextColor(event.getEventColor());
                } else {
                    eventsTextViewList[index].setVisibility(INVISIBLE);
                }

                dayContainerList.add(model);

                final int finalIndex = index;
                days[index].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (mSelectedTexView != null) {
                            mSelectedLinearLayout.setBackgroundResource(android.R.color.transparent);
                            mSelectedTexView.setTextColor(mPreviousColor);
                        }
                        mPreviousColor = mOffMonthDayColor;
                        mSelectedTexView = days[finalIndex];
                        daysContainer[finalIndex].setBackgroundResource(R.drawable.drawable_circle);

                        Drawable backgroundDrawable = DrawableCompat.wrap(daysContainer[finalIndex].getBackground()).mutate();
                        DrawableCompat.setTint(backgroundDrawable, mSelectorColor);

                        mSelectedLinearLayout = daysContainer[finalIndex];
                        days[finalIndex].setTextColor(mSelectedDayTextColor);


                        if (mCalenderDayClickListener != null) {
                            mCalenderDayClickListener.onGetDay(model);
                        }
                    }
                });

                prevCurrentDay++;
                index++;
            }
        }


        // now set current month date

        mCalendar.set(selectedYear, selectedMonth, 1);

        for (int i = 1; i <= daysInCurrentMonth; i++) {
            days[index].setText(String.valueOf(i));
            days[index].setTextColor(mCurrentMonthDayColor);

            final DayContainerModel model = new DayContainerModel();
            model.setIndex(index);
            model.setYear(selectedYear);
            model.setMonthNumber(selectedMonth);
            model.setMonth(MONTH_NAMES[selectedMonth]);
            model.setDay(i);
            String date = model.getDay() + " " + model.getMonth() + " " + model.getYear();
            model.setDate(date);
            model.setTimeInMillisecond(TimeUtil.getTimestamp(date));

            Event event = getEvent(model.getTimeInMillisecond());
            model.setEvent(event);
            model.setHaveEvent(event != null);

            if (mToday.equals(model.getDate())) {
                if (mSelectedTexView != null) {
                    mSelectedLinearLayout.setBackgroundResource(android.R.color.transparent);
                    mSelectedTexView.setTextColor(mPreviousColor);
                }
                mPreviousColor = mCurrentMonthDayColor;
                mSelectedTexView = days[index];
                //1 выделенная дата
                daysContainer[index].setBackgroundResource(R.drawable.drawable_circle);

                Drawable backgroundDrawable = DrawableCompat.wrap(daysContainer[index].getBackground()).mutate();
                DrawableCompat.setTint(backgroundDrawable, mSelectorColor);

                mSelectedLinearLayout = daysContainer[index];
                days[index].setTextColor(mSelectedDayTextColor);
                TextView mSelectedday = days[index];
            }


            if (model.isHaveEvent()) {
                eventsTextViewList[index].setText(event.getEventText());
                eventsTextViewList[index].setVisibility(VISIBLE);
                eventsTextViewList[index].setTextColor(event.getEventColor());
            } else {
                eventsTextViewList[index].setVisibility(INVISIBLE);
            }

            dayContainerList.add(model);

            final int finalIndex = index;
            days[index].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Hello");

                  if (mSelectedTexView != null) {
                        mSelectedLinearLayout.setBackgroundResource(android.R.color.transparent);
                        mSelectedTexView.setTextColor(mPreviousColor);
                      mSelectedTexView.setBackgroundResource(android.R.color.transparent);
                    }
                    mPreviousColor = mCurrentMonthDayColor;
                    mSelectedTexView = days[finalIndex];
                    //все даты
                    daysContainer[finalIndex].setBackgroundResource(R.drawable.drawable_rectangular);

                    Drawable backgroundDrawable = DrawableCompat.wrap(daysContainer[finalIndex].getBackground()).mutate();
                    DrawableCompat.setTint(backgroundDrawable, mSelectorColor);

                    //эксперименты
                    mSelectedLinearLayout = daysContainer[finalIndex];
                    days[finalIndex].setTextColor(mSelectedDayTextColor);
                    days[finalIndex].setBackgroundResource(R.drawable.drawable_circle);

                    if (mCalenderDayClickListener != null) {
                        mCalenderDayClickListener.onGetDay(model);
                    }
                }
            });

            index++;
        }

        int nextYear;
        int nextMonth;
        if (selectedMonth < 11) {
            nextYear = selectedYear;
            nextMonth = selectedMonth + 1;
        } else {
            nextYear = selectedYear + 1;
            nextMonth = 0;
        }

        // now set rest of day
        for (int i = 1; i <= nextMonthDays; i++) {
            days[index].setText(String.valueOf(i));
            days[index].setTextColor(mOffMonthDayColor);

            final DayContainerModel model = new DayContainerModel();
            model.setIndex(index);
            model.setYear(nextYear);
            model.setMonthNumber(nextMonth);
            model.setMonth(MONTH_NAMES[nextMonth]);
            model.setDay(i);
            String date = model.getDay() + " " + model.getMonth() + " " + model.getYear();
            model.setDate(date);
            model.setTimeInMillisecond(TimeUtil.getTimestamp(date));

            Event event = getEvent(model.getTimeInMillisecond());
            model.setEvent(event);
            model.setHaveEvent(event != null);

            if (model.isHaveEvent()) {
                eventsTextViewList[index].setText(event.getEventText());
                eventsTextViewList[index].setVisibility(VISIBLE);
                eventsTextViewList[index].setTextColor(event.getEventColor());

            } else {
                eventsTextViewList[index].setVisibility(INVISIBLE);
            }

            dayContainerList.add(model);

            final int finalIndex = index;
            days[index].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Hello");
                    if (mSelectedTexView != null) {
                        mSelectedTexView.setTextColor(mPreviousColor);
                        mSelectedLinearLayout.setBackgroundResource(android.R.color.transparent);
                    }
                    mPreviousColor = mOffMonthDayColor;
                    mSelectedTexView = days[finalIndex];
                    mSelectedLinearLayout = daysContainer[finalIndex];
                    daysContainer[finalIndex].setBackgroundResource(R.drawable.drawable_circle);

                    Drawable backgroundDrawable = DrawableCompat.wrap(daysContainer[finalIndex].getBackground()).mutate();
                    DrawableCompat.setTint(backgroundDrawable, mSelectorColor);

                    days[finalIndex].setTextColor(mSelectedDayTextColor);

                    if (mCalenderDayClickListener != null) {
                        mCalenderDayClickListener.onGetDay(model);
                    }
                }
            });

            index++;
        }

    }

    private void gotoNextMonth() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);

        if (month < 11) {
            mCalendar.set(year, month + 1, 1);
        } else {
            mCalendar.set(year + 1, 0, 1);
        }

        initCalender(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH));
    }

    private void gotoPreviousMonth() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);

        if (month > 0) {
            mCalendar.set(year, month - 1, 1);
        } else {
            mCalendar.set(year - 1, 11, 1);
        }

        initCalender(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH));
    }


    private void initDaysInCalender(LayoutParams buttonParams, Context context, DisplayMetrics metrics) {
        int engDaysArrayCounter = 0;

        for (int weekNumber = 0; weekNumber < 6; ++weekNumber) {
            for (int dayInWeek = 0; dayInWeek < 7; ++dayInWeek) {

                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setLayoutParams(buttonParams);
                linearLayout.setOrientation(VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);

//                RelativeLayout relativeLayout = new RelativeLayout(context);
//                relativeLayout.setLayoutParams(buttonParams);
//                relativeLayout.setGravity(Gravity.CENTER);

                final TextView day = new TextView(context);
                day.setTextColor(Color.parseColor(CUSTOM_GREY));
                day.setBackgroundColor(Color.TRANSPARENT);
                day.setLayoutParams(new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                day.setTextSize((int) metrics.density * 5);
                day.setSingleLine();
                day.setPadding(10, 10, 10, 10);
                day.setGravity(Gravity.CENTER);


                TextView textView = new TextView(context);
                textView.setLayoutParams(new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,  // change MATCH_PARENT
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setTextSize(8);
                textView.setMaxLines(1);

                //длина
                int maxLength = 11;
                InputFilter[] fArray = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(maxLength);
                textView.setFilters(fArray);
                textView.setText("H");
                textView.setPadding(3, 2, 3, 1 );
                textView.setGravity(Gravity.CENTER);
                textView.setVisibility(INVISIBLE);
//textView.setBackgroundResource(R.drawable.shape_of_summ);
textView.setTextColor(Color.WHITE);
                textView.setBackground(getResources().getDrawable(R.drawable.shape_of_summ));

     /*           TextView textView2 = new TextView(context);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,  // change MATCH_PARENT
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                textView2.setTextSize(8);
                textView2.setMaxLines(1);
                InputFilter[] fArray1 = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(maxLength);
                textView2.setFilters(fArray);
                textView2.setText("R");
                textView2.setGravity(Gravity.CENTER);
                textView2.setVisibility(VISIBLE);*/


                linearLayout.addView(day);
                linearLayout.addView(textView);
           //     linearLayout.addView(textView2);


                days[engDaysArrayCounter] = day;
                eventsTextViewList[engDaysArrayCounter] = textView;

                weeks[weekNumber].addView(linearLayout);

                daysContainer[engDaysArrayCounter] = linearLayout;

                engDaysArrayCounter++;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CalenderEvent);

        mBackgroundColor = typedArray.getColor(R.styleable.CalenderEvent_calender_background, DEFAULT_BACKGROUND_COLOR);

        mSelectorColor = typedArray.getColor(R.styleable.CalenderEvent_selector_color, DEFAULT_SELECTOR_COLOR);

        mSelectedDayTextColor = typedArray.getColor(R.styleable.CalenderEvent_selected_day_text_color, DEFAULT_SELECTED_DAY_COLOR);

        mCurrentMonthDayColor = typedArray.getColor(R.styleable.CalenderEvent_current_month_day_color, DEFAULT_CURRENT_MONTH_DAY_COLOR);

        mOffMonthDayColor = typedArray.getColor(R.styleable.CalenderEvent_off_month_day_color, DEFAULT_OFF_MONTH_DAY_COLOR);

        mMonthTextColor = typedArray.getColor(R.styleable.CalenderEvent_month_color, DEFAULT_TEXT_COLOR);

        mWeekNameColor = typedArray.getColor(R.styleable.CalenderEvent_week_name_color, DEFAULT_TEXT_COLOR);

        nextButtonDrawable = typedArray.getDrawable(R.styleable.CalenderEvent_next_icon);

        prevButtonDrawable = typedArray.getDrawable(R.styleable.CalenderEvent_previous_icon);


        typedArray.recycle();
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mainView = inflater.inflate(R.layout.main_calender, this);
        }
    }

    private LayoutParams getdaysLayoutParams() {
        LayoutParams buttonParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        buttonParams.weight = 1;
        return buttonParams;
    }

    private DayContainerModel getDaysContainerModel(String date) {
        for (DayContainerModel model : dayContainerList) {
            if (model.getDate().equals(date)) {
                return model;
            }
        }
        return null;
    }

    private Event getEvent(long time) {
        String date = TimeUtil.getDate(time);

        String textKey = date + TEXT_EVENT;
        String colorKey = date + COLOR_EVENT;

        String eventText = preferenceHelper.readString(textKey);

        if (eventText != null) {
            int color = preferenceHelper.readInteger(colorKey);
            if (color == 0) {
                color = DEFAULT_TEXT_COLOR;
            }
            return new Event(time, eventText, color);
        }

        return null;
    }


    /*
     * ******************* User method ***************
     * */

    public void addEvent(Event event) {
        if (event == null) return;
        String date = TimeUtil.getDate(event.getTime());

        String textKey = date + TEXT_EVENT;
        String colorKey = date + COLOR_EVENT;

        preferenceHelper.write(textKey, event.getEventText());
        preferenceHelper.write(colorKey, event.getEventColor());

        DayContainerModel model = getDaysContainerModel(date);
        if (model != null) {
            model.setHaveEvent(true);
            model.setEvent(event);
        }
    }

    public void removeEvent(Event event) {
        if (event == null) return;
        String date = TimeUtil.getDate(event.getTime());
        String textKey = date + TEXT_EVENT;
        String colorKey = date + COLOR_EVENT;
        preferenceHelper.remove(textKey);
        preferenceHelper.remove(colorKey);

        DayContainerModel model = getDaysContainerModel(date);
        if (model != null) {
            model.setHaveEvent(false);
            model.setEvent(null);
        }
    }

    public void initCalderItemClickCallback(CalenderDayClickListener listener) {
        this.mCalenderDayClickListener = listener;
    }


}
