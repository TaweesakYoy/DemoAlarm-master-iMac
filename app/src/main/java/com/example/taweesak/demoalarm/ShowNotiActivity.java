package com.example.taweesak.demoalarm;


import android.app.Notification;
import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShowNotiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_noti);

        showNotification();

    }

    private void showNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_action_noti);
        builder.setTicker("Ticker Notification");
        builder.setContentTitle("Title Notification");
        builder.setContentText("Text Nofication");
        builder.setWhen(System.currentTimeMillis()); // show Noti current time
        builder.setAutoCancel(false);
        builder.setOnlyAlertOnce(true);

        Uri uri = RingtoneManager.getDefaultUri(Notification.DEFAULT_SOUND);

        builder.setSound(uri);

        Notification notification = builder.build();  // Pack Value from Top
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000,notification); // 1000 is noti ID

    }
}
