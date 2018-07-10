package com.secondsave.health_med.Fragments.Reminders;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.secondsave.health_med.R;

/**
 * Created by shiva on 8/4/17.
 */

public class Alarms extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Wake up", Toast.LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.add_button))
//                .setColor(context.getResources().getColor(R.color.background_color))
//                .setContentTitle(context.getString(R.string.))
//                .setContentIntent(notificationPendingIntent)
//                .setContentText(String.format(context.getString(R.string.notification), viewObject.getTitle()))
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setStyle(bigText)
//                .setPriority(NotificationManager.IMPORTANCE_HIGH)
    }
}

