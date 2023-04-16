package my.edu.utar.periodtracker;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    private static final int NOTIFICATION_ID_SLEEP_REMINDER = 1;
    private static final int NOTIFICATION_ID_DRINK_REMINDER = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationTitle = "";
        String notificationMessage = "";
        int notificationId = 0;

        // Get the notification title, message, and ID based on the intent action
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case "ACTION_SHOW_SLEEP_REMINDER":
                    notificationTitle = "Sleep Reminder";
                    notificationMessage = "It's time to go to bed.";
                    notificationId = NOTIFICATION_ID_SLEEP_REMINDER;
                    break;
                case "ACTION_SHOW_DRINK_REMINDER":
                    notificationTitle = "Drink Reminder";
                    notificationMessage = "It's time to stay hydrated.";
                    notificationId = NOTIFICATION_ID_DRINK_REMINDER;
                    break;
            }
        }

        // Create a notification intent to be shown when the notification is clicked
        Intent notificationIntent = new Intent(context, ReminderActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel1")
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());

    }
}
