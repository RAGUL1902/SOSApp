package com.example.sosapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ScreenReceiver extends BroadcastReceiver {


    public  String userNumber,emergencyNumber1, emergencyNumber2,emergencyNumber3;


    public int count = 0;
    public long  timeBefore = System.currentTimeMillis();
    public long timeNow;
    String address;


    ScreenReceiver(String UserNumber, String EmergencyNumber1,String EmergencyNumber2,String EmergencyNumber3, String Address){
        userNumber = UserNumber;
        emergencyNumber3 = EmergencyNumber3;
        emergencyNumber1 = EmergencyNumber1;
        emergencyNumber2 =EmergencyNumber2;
        address = Address;

    }


    @Override
    public void onReceive(final Context context, final Intent intent) {

        timeNow = System.currentTimeMillis();
        if(timeNow - timeBefore< 1000){
            count = count + 1;
        }
        else {
            count = 0;
        }
        if (count>= 3){
            Log.e("Check", "Fourtimes Pressed, Emergency!!!");
            String message = "I m in danger, HELP ME!!!"  + address;
            Log.e("receiver",message);

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(emergencyNumber1, userNumber, message, null, null);
            smsManager.sendTextMessage(emergencyNumber2, userNumber, message, null, null);
            smsManager.sendTextMessage(emergencyNumber3, userNumber, message, null, null);



        }
        timeBefore = timeNow;
    }


}
