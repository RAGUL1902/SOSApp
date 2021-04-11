package com.example.sosapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.Random;

public class ShakeService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    public  String userNumber,emergencyNumber1, emergencyNumber2,emergencyNumber3,address;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle b =intent.getExtras();
        userNumber = b.getString("userNumber");
        emergencyNumber1 =b.getString("emergencyNumber1");
        emergencyNumber2 = b.getString("emergencyNumber2");
        emergencyNumber3 =b.getString("emergencyNumber3");
        address = b.getString("Address");
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_UI, new Handler());

        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter

        if (mAccel > 11) {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            Log.e("ShakeService","Shaking detected");

            String message = "I m in danger, HELP ME!!!"  + address;
            Log.e("receiver",message);

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(emergencyNumber1, userNumber, message, null, null);
            smsManager.sendTextMessage(emergencyNumber2, userNumber, message, null, null);
            smsManager.sendTextMessage(emergencyNumber3, userNumber, message, null, null);

        }
    }

}