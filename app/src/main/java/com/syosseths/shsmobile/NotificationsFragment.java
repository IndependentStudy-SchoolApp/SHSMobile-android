package com.syosseths.shsmobile;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class NotificationsFragment extends Fragment {

    private Switch notifSwitch;
    private TimePicker timePicker;
    private SharedPreferences sharedPreferences;
    private Button saveButton;
    private int notifHour, notifMinute;


    @TargetApi(Build.VERSION_CODES.O_MR1)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);

        notifSwitch = rootView.findViewById(R.id.notifSwitch);
        timePicker = rootView.findViewById(R.id.timePicker);
        saveButton = rootView.findViewById(R.id.saveButton);

        sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);

        notifHour = sharedPreferences.getInt("notifHour", 6);
        notifMinute = sharedPreferences.getInt("notifMinute", 0);

        // support for api under 23
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            timePicker.setCurrentHour(notifHour);
            timePicker.setCurrentMinute(notifMinute);
        } else {
            timePicker.setHour(notifHour);
            timePicker.setMinute(notifMinute);
        }

        timePicker.setScaleX(1.1f);
        timePicker.setScaleY(1.1f);

        notifSwitch.setChecked(sharedPreferences.getBoolean("notifEnabled", false));

        saveButton.setOnClickListener((View) -> saveChanges());

        return rootView;
    }


    @TargetApi(Build.VERSION_CODES.O_MR1)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private void saveChanges() {
        // support for api under 23
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            timePicker.getCurrentHour();
            timePicker.getCurrentMinute();
        } else {
            notifHour = timePicker.getHour();
            notifMinute = timePicker.getMinute();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notifEnabled", notifSwitch.isChecked());
        editor.putInt("notifHour", notifHour);
        editor.putInt("notifMinute", notifMinute);
        editor.apply();

        Toast.makeText(this.getContext(), "Changes have been saved!", Toast.LENGTH_SHORT).show();

        if (notifSwitch.isChecked())
            setNotification(notifHour, notifMinute);

    }

    @TargetApi(Build.VERSION_CODES.O_MR1)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private void setNotification(int notifHour, int notifMinute) {

        Intent notifIntent = new Intent(getContext(), AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, notifIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, notifHour);
        calendar.set(Calendar.MINUTE, notifMinute);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            //else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            //    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(this.getContext(), "Notifications enabled!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this.getContext(), "Notifications failed to enable, please retry and check your notification settings", Toast.LENGTH_SHORT).show();
    }
}
