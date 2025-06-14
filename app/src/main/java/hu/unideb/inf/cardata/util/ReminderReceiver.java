package hu.unideb.inf.cardata.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String carName = intent.getStringExtra("carName");
        NotificationHelper.createNotification(context, carName);

        int carId = intent.getIntExtra("carId", -1);
        long intervalDays = intent.getLongExtra("oilInterval", 0);
        long now = System.currentTimeMillis();
        long nextReminder = now + intervalDays * 24L * 60L * 60L * 1000L;

        Intent newIntent = new Intent(context, ReminderReceiver.class);
        newIntent.putExtra("carName", carName);
        newIntent.putExtra("carId", carId);
        newIntent.putExtra("oilInterval", intervalDays);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, carId, newIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, nextReminder, pendingIntent);
        }
}