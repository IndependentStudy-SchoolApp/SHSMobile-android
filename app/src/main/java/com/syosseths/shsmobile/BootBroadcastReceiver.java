package com.syosseths.shsmobile;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class BootBroadcastReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context pContext, Intent intent) {
        //int notifHour = sharedPreferences.getInt("notifHour", 6);
        //int notifMinute = sharedPreferences.getInt("notifMinute", 0);
        //setNotification(notifHour, notifMinute);
    }
}