package hu.unideb.inf.cardata.util;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import java.util.Date;
import java.util.Random;
import hu.unideb.inf.cardata.R;
import hu.unideb.inf.cardata.model.Car;

public class NotificationHelper {
    public static void scheduleOilChangeReminder(Context context, Car car) {
        long nextTime = System.currentTimeMillis() + 10 * 1000; // 10 mp késleltetés
        //long nextTime = car.lastOilChange + car.oilChangeIntervalDays * 24L * 60L * 60L * 1000L;
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("carName", car.name);
        intent.putExtra("carId", car.id);
        intent.putExtra("oilInterval", car.oilChangeIntervalDays);


        Log.d("NotificationHelper", "Scheduled alarm for: " + new Date(nextTime));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                car.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, nextTime, pendingIntent);
    }

    public static void createNotification(Context context, String carName) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "oil_reminders";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Olajcsere értesítések",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
            Log.d("Reminder", "Notification channel létrehozva");
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.car_launcher)  // <- ideiglenes ikon
                .setContentTitle("Emlékeztető")
                .setContentText(carName + " ideje olajcserére!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        manager.notify(new Random().nextInt(), builder.build());
        Log.d("Reminder", "Értesítés megjelenítve");
    }

}
