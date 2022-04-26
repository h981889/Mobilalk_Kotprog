package com.example.kotprog.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.kotprog.R;

public class NotificationHandler
{
    private static final String NOT_ID = "FOCI_APP";
    private static final String CHANEL_ID = "0";
    private NotificationManager manager;
    private Context context;

    public NotificationHandler(Context context) {
        this.context = context;
        this.manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        createChanel();
    }

    private void createChanel()
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
        {
            return;
        }
        else
        {
            NotificationChannel channel = new NotificationChannel(NOT_ID, "Foci notification", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setDescription("Foci App Notification");

            this.manager.createNotificationChannel(channel);
        }
    }

    public void send(String massage)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOT_ID);
        builder.setContentTitle("Foci App");
        builder.setContentText(massage);
        builder.setSmallIcon(R.drawable.foci);

        this.manager.notify(0,builder.build());
    }
}
