package my.edu.utar.periodtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    CompactCalendarView compactCalendarView;
    DatabaseHelper db;

    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private TextView title;
    private TextView txStart;
    private TextView txSymptom;
    private TextView txSleep;
    private TextView txNote;
    private String email;
    final String PERIOD_DAY_COLOR = "#d45685";
    final String PREDICT_PERIOD_COLOR = "#f9aeab";
    final String OVULATION_DAY_COLOR = "#7b73b0";
    final String OVULATION_WEEK_COLOR = "#a79bff";

    Calendar endPeriod = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        txStart = findViewById(R.id.textView_start);
        txSymptom = findViewById(R.id.textView_symptom);
        txSleep = findViewById(R.id.textView_sleep);
        txNote = findViewById(R.id.textView_note);

        SharedPreferences pref = getSharedPreferences("PeriodTrackerPreferences", MODE_PRIVATE);
        db = new DatabaseHelper(this);
        email = pref.getString("email", "NA");
        init();


//        Calendar startPeriod = Calendar.getInstance();
//        startPeriod.set(Calendar.YEAR, 2023);
//        startPeriod.set(Calendar.MONTH, Calendar.APRIL);
//        startPeriod.set(Calendar.DAY_OF_MONTH, 4);
//
//        Calendar calculateOvulation = startPeriod;
//        Calendar calculatePredict = startPeriod;
//        for(int i = 0; i < 7; i++){
//            Date periodDay = startPeriod.getTime();
//            Event ev1 = new Event(Color.parseColor(PERIOD_DAY_COLOR), periodDay.getTime());
//            compactCalendarView.addEvent(ev1);
//            startPeriod.add(Calendar.DAY_OF_YEAR, 1);
//        }
//
//
//        calculatePredict.add(Calendar.DAY_OF_YEAR, 28);
//        for(int i = 0; i < 7; i++){
//            Date predictDay = calculatePredict.getTime();
//            Event ev2 = new Event(Color.parseColor(PREDICT_PERIOD_COLOR), predictDay.getTime());
//            compactCalendarView.addEvent(ev2);
//            calculatePredict.add(Calendar.DAY_OF_YEAR, 1);
//        }
//
//        calculateOvulation.add(Calendar.DAY_OF_YEAR, 15);
//        Date ovulationDay = calculateOvulation.getTime();
//        Event ev3 = new Event(Color.parseColor(OVULATION_DAY_COLOR), ovulationDay.getTime());
//        compactCalendarView.addEvent(ev3);
//        for (int i = 0; i < 5; i++){
//            calculateOvulation.add(Calendar.DAY_OF_YEAR, -1);
//            Date ovulationWeek = calculatePredict.getTime();
//            Event ev4 = new Event(Color.parseColor(OVULATION_WEEK_COLOR), ovulationWeek.getTime());
//            compactCalendarView.addEvent(ev4);
//        }



        txStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txStart.getText() == "Start Period"){

                }
                else if (txStart.getText() == "End Period"){

                }
            }
        });

        txSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, Symptom.class);
                startActivity(intent);
            }
        });

        txSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //List<Event> events = compactCalendarView.getEvents(dateClicked);
                //Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                title.setText(dateFormatMonth.format(firstDayOfNewMonth));


            }
        });

    }

    private void init(){

        compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setEventIndicatorStyle(1);

        title = findViewById(R.id.textViewMonth);
        title.setText(dateFormatMonth.format(new Date(System.currentTimeMillis())));

        ShapeDrawable period = new ShapeDrawable(new ArcShape(0, 360));
        period.setIntrinsicWidth(50);
        period.setIntrinsicHeight(50);
        period.getPaint().setColor(Color.parseColor(PERIOD_DAY_COLOR));

        TextView txPeriod = findViewById(R.id.textView_period);
        txPeriod.setCompoundDrawablesWithIntrinsicBounds(period, null, null, null);
        txPeriod.setPadding(25, 20, 0, 20);

        ShapeDrawable predict = new ShapeDrawable(new ArcShape(0, 360));
        predict.setIntrinsicWidth(50);
        predict.setIntrinsicHeight(50);
        predict.getPaint().setColor(Color.parseColor(PREDICT_PERIOD_COLOR));
        TextView txPredict = findViewById(R.id.textView_predict);
        txPredict.setCompoundDrawablesWithIntrinsicBounds(predict, null, null, null);
        txPredict.setPadding(16, 20, 0, 20);

        ShapeDrawable ovulation = new ShapeDrawable(new ArcShape(0, 360));
        ovulation.setIntrinsicWidth(50);
        ovulation.setIntrinsicHeight(50);
        ovulation.getPaint().setColor(Color.parseColor(OVULATION_DAY_COLOR));
        TextView txOvulation = findViewById(R.id.textView_ovulation);
        txOvulation.setCompoundDrawablesWithIntrinsicBounds(ovulation, null, null, null);
        txOvulation.setPadding(16, 20, 0, 20);

        // check the current day is in period or end period

        setPeriodEvent();

    }

    private void setPeriodEvent(){
        HashMap<String, String> previousPeriod = db.selectPreviousPeriod(email);
        Calendar startPeriod = Calendar.getInstance();
        Calendar endPeriod = Calendar.getInstance();
        Calendar currentDay = Calendar.getInstance();

        try{
            Date periodDay = dateFormatDay.parse(previousPeriod.get("startPeriod"));
            startPeriod.setTime(periodDay);
            if(Integer.parseInt(previousPeriod.get("periodTimes")) == 1){

                if((int) ((currentDay.getTimeInMillis() - startPeriod.getTimeInMillis()) / (1000 * 60 * 60 * 24)) + 1 > 7){

                    long endMili = startPeriod.getTimeInMillis() + (7 * 24 * 60 * 60 * 1000);
                    db.updateEndPeriod(dateFormatDay.format(new Date(endMili)), 7, email, previousPeriod.get("periodTimes"));
                }


            }

            if(previousPeriod.get("endPeriod") == null) {
                txStart.setText("End Period");
                //current day - start period day

                for(int i = 0; i < (int) ((currentDay.getTimeInMillis() - startPeriod.getTimeInMillis()) / (1000 * 60 * 60 * 24)) + 1; i++){
                    periodDay = startPeriod.getTime();
                    Event newUserEvt = new Event(Color.parseColor(PERIOD_DAY_COLOR), periodDay.getTime());
                    compactCalendarView.addEvent(newUserEvt);
                    startPeriod.add(Calendar.DAY_OF_YEAR, 1);
                }

            }
            else {
                txStart.setText("Start Period");
                endPeriod.setTime(dateFormatDay.parse(previousPeriod.get("endPeriod")));

                for(int i = 0; i < (int) ((endPeriod.getTimeInMillis() - startPeriod.getTimeInMillis()) / (1000 * 60 * 60 * 24)) + 1; i++){
                    periodDay = startPeriod.getTime();
                    Event newUserEvt = new Event(Color.parseColor(PERIOD_DAY_COLOR), periodDay.getTime());
                    compactCalendarView.addEvent(newUserEvt);
                    startPeriod.add(Calendar.DAY_OF_YEAR, 1);
                }

            }


        }catch (Exception e){

        }



    }


}