package com.syosseths.jweis.shsmobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class BootBroadcastReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context pContext, Intent intent) {;
        //int timeHour = sharedPreferences.getInt("notifHour", 6);
        //int timeMinute = sharedPreferences.getInt("notifMinute", 0);
        //setNotification(timeHour, timeMinute);
    }
}