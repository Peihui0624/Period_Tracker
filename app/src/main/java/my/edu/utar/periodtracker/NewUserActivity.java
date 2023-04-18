package my.edu.utar.periodtracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewUserActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Calendar calendar;
    private Button confirmBtn;
    private String userEmail;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        SharedPreferences pref = getSharedPreferences("PeriodTrackerPreferences", MODE_PRIVATE);
        userEmail = pref.getString("email", "NA");

        db = new DatabaseHelper(this);

        initDatePicker();
        dateButton = findViewById(R.id.startDatePicker);
        dateButton.setText(getTodaysDate());

        confirmBtn = findViewById(R.id.btnNewUser_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                boolean isInserted = db.insertPeriod(sdf.format(calendar.getTime()), 1, 1, userEmail);

                if(isInserted){
                    Toast.makeText(getApplicationContext(), "Welcome to Period Tracker!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewUserActivity.this, CalendarActivity.class);
                    intent.putExtra("initPeriodDay", calendar);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Configuration failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        calendar = cal;
        return makeDateString(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // arg1 = year
                // arg2 = month
                // arg3 = day
                //month = month + 1;
                String date = makeDateString(day, month + 1, year);
                dateButton.setText(date);
                calendar.set(year, month, day);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
    }

    private String makeDateString (int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month){
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

}