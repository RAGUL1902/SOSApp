package com.example.sosapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;
import java.util.concurrent.Executor;

public class NavigationService extends Service {



    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChanel();
        createNotificationObject();
        Bundle b = intent.getExtras();
        powerButtonReceiver(b.getString("userNumber"), b.getString("emergencyNumber1"),
                b.getString("emergencyNumber2"),b.getString("emergencyNumber3"),b.getString("Address"));
        return START_STICKY;
    }

    private void powerButtonReceiver(String userNumber, String emergencyNumber1, String emergencyNumber2, String emergencyNumber3,String address) {
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        final BroadcastReceiver mReceiver = new ScreenReceiver(userNumber, emergencyNumber1,emergencyNumber2,emergencyNumber3,address);
        registerReceiver(mReceiver, filter);
    }

    private void createNotificationObject() {
        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent1,0);
        Notification notification = new NotificationCompat.Builder(this,"ChanelId1")
                .setContentTitle("SOSApp")
                .setContentText("Location is Monitored")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();
        startForeground(1,notification);
    }

    private void createNotificationChanel() {
        NotificationChannel notificationChannel = new NotificationChannel(
                "ChanelId1","Location Notification", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(notificationChannel);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }

    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }
}
