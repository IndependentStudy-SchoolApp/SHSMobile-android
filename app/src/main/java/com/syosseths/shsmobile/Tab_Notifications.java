package com.syosseths.shsmobile;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class Tab_Notifications extends Fragment {

    Switch notifSwitch;
    TimePicker timePicker;
    SharedPreferences sharedPreferences;
    Button saveButton;
    int timeHour;
    int timeMinute;


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab_notifications, container, false);

        notifSwitch = rootView.findViewById(R.id.switch1);
        timePicker = rootView.findViewById(R.id.timePicker1);
        saveButton = rootView.findViewById(R.id.saveChanges_button);

        sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);

        timeHour = sharedPreferences.getInt("notifHour", 6);
        timeMinute = sharedPreferences.getInt("notifMinute", 0);

        // support for nox emulator api 19
        if (Build.VERSION.SDK_INT < 23) {
            timePicker.setCurrentHour(timeHour);
            timePicker.setCurrentMinute(timeMinute);
        } else {
            timePicker.setHour(timeHour);
            timePicker.setMinute(timeMinute);
        }

        timePicker.setScaleX((float) 1.1);
        timePicker.setScaleY((float) 1.1);

        notifSwitch.setChecked(sharedPreferences.getBoolean("notifEnabled", false));

        View v = new View(getContext());
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveChanges();
            }
        });


        return rootView;
    }


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void saveChanges() {
        timeHour = timePicker.getHour();
        timeMinute = timePicker.getMinute();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notifEnabled", notifSwitch.isChecked());
        editor.putInt("notifHour", timeHour);
        editor.putInt("notifMinute", timeMinute);
        editor.apply();

        if (notifSwitch.isChecked()) {
            setNotification(timeHour, timeMinute);
        }

        Toast.makeText(this.getContext(), "Changes have been saved!", Toast.LENGTH_SHORT).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setNotification(int userHour, int userMinute) {

        Intent alarmIntent = new Intent(getContext(), AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, alarmIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, userHour);
        calendar.set(Calendar.MINUTE, userMinute);
        calendar.set(Calendar.SECOND, 1);

        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        if (manager != null)
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }
}
