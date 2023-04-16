package my.edu.utar.periodtracker;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID_SLEEP_REMINDER = 1;
    private static final int NOTIFICATION_ID_DRINK_REMINDER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        // Find the Switch by its ID
        Switch switchNotification = findViewById(R.id.switchNotification);
        // Get the NotificationManager instance
        notificationManager = (NotificationManager) getSystemService(MainActivity.NOTIFICATION_SERVICE);

        // Set an OnCheckedChangeListener for the Switch
        switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Toggle notifications based on switch state
            if (isChecked) {
                Toast.makeText(ReminderActivity.this, "Reminder is allowed!", Toast.LENGTH_SHORT).show();
                enableNotifications();
            } else {
                disableNotifications();
            }
        });
    }
    private void enableNotifications() {
        // Create notification channels if running on Android Oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel("channel1", "Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel1);
        }

        // Get the current time
        Calendar calendar = Calendar.getInstance();

        // Set the time for sleep reminder notification (e.g. 10:00 PM)
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long sleepReminderTime = calendar.getTimeInMillis();

        // Set the time for drink reminder notification (e.g. 2:30 PM)
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long drinkReminderTime = calendar.getTimeInMillis();

        // Schedule the sleep reminder notification
        Intent sleepReminderIntent = new Intent(this, ReminderBroadcast.class);
        PendingIntent sleepReminderPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID_SLEEP_REMINDER, sleepReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, sleepReminderTime, sleepReminderPendingIntent);

        // Schedule the drink reminder notification
        Intent drinkReminderIntent = new Intent(this, ReminderBroadcast.class);
        PendingIntent drinkReminderPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID_DRINK_REMINDER, drinkReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, drinkReminderTime, drinkReminderPendingIntent);
    }

    private void disableNotifications() {
        // Cancel sleep reminder notification
        Intent sleepReminderIntent = new Intent(this, ReminderBroadcast.class);
        PendingIntent sleepReminderPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID_SLEEP_REMINDER, sleepReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sleepReminderPendingIntent);

        // Cancel drink reminder notification
        Intent drinkReminderIntent = new Intent(this, ReminderBroadcast.class);
        PendingIntent drinkReminderPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID_DRINK_REMINDER, drinkReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(drinkReminderPendingIntent);
    }
}