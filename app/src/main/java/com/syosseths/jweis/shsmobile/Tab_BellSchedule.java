package com.syosseths.jweis.shsmobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.syosseths.jweis.shsmobile.R;

public class Tab_BellSchedule extends Fragment {

    Spinner schedSpinner;
    TextView p1Time, p2Time, p3Time, p4Time, p5Time, p6Time, p7Time, p8Time, p9Time;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_bellschedule, container, false);

        schedSpinner = (Spinner) rootView.findViewById(R.id.spinner_bellschedules);
        p1Time = (TextView) rootView.findViewById(R.id.p1Time);
        p2Time = (TextView) rootView.findViewById(R.id.p2Time);
        p3Time = (TextView) rootView.findViewById(R.id.p3Time);
        p4Time = (TextView) rootView.findViewById(R.id.p4Time);
        p5Time = (TextView) rootView.findViewById(R.id.p5Time);
        p6Time = (TextView) rootView.findViewById(R.id.p6Time);
        p7Time = (TextView) rootView.findViewById(R.id.p7Time);
        p8Time = (TextView) rootView.findViewById(R.id.p8Time);
        p9Time = (TextView) rootView.findViewById(R.id.p9Time);

        //View v = new View(getContext());

        schedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String schedule = schedSpinner.getSelectedItem().toString();
                //Log.d("Schedspinner String: ", schedule);
                if(position==0)
                {
                    p1Time.setText(getResources().getString(R.string.p1Time));
                    p2Time.setText(getResources().getString(R.string.p2Time));
                    p3Time.setText(getResources().getString(R.string.p3Time));
                    p4Time.setText(getResources().getString(R.string.p4Time));
                    p5Time.setText(getResources().getString(R.string.p5Time));
                    p6Time.setText(getResources().getString(R.string.p6Time));
                    p7Time.setText(getResources().getString(R.string.p7Time));
                    p8Time.setText(getResources().getString(R.string.p8Time));
                    p9Time.setText(getResources().getString(R.string.p9Time));
                }
                else
                {
                    p1Time.setText(getResources().getString(R.string.p1Time2));
                    p2Time.setText(getResources().getString(R.string.p2Time2));
                    p3Time.setText(getResources().getString(R.string.p3Time2));
                    p4Time.setText(getResources().getString(R.string.p4Time2));
                    p5Time.setText(getResources().getString(R.string.p5Time2));
                    p6Time.setText(getResources().getString(R.string.p6Time2));
                    p7Time.setText(getResources().getString(R.string.p7Time2));
                    p8Time.setText(getResources().getString(R.string.p8Time2));
                    p9Time.setText(getResources().getString(R.string.p9Time2));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        return rootView;
    }
}