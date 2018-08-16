package com.syosseths.shsmobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class GradeCalculatorFragment extends Fragment {

    String q1, q2, q3, q4, mt, fn;
    GradeCalculator gradeCalculator;
    TextView finalGradeLabel;
    Button calcGradeButton;
    private Spinner spinnerQ1, spinnerQ2, spinnerQ3, spinnerQ4, spinnerMT, spinnerFN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grade_calculator, container, false);

        finalGradeLabel = rootView.findViewById(R.id.finalGradeLabel);

        spinnerQ1 = rootView.findViewById(R.id.spinnerQ1);
        spinnerQ2 = rootView.findViewById(R.id.spinnerQ2);
        spinnerQ3 = rootView.findViewById(R.id.spinnerQ3);
        spinnerQ4 = rootView.findViewById(R.id.spinnerQ4);
        spinnerMT = rootView.findViewById(R.id.spinnerMT);
        spinnerFN = rootView.findViewById(R.id.spinnerFN);

        calcGradeButton = rootView.findViewById(R.id.calcGradeButton);

        gradeCalculator = new GradeCalculator(q1, q2, q3, q4, mt, fn);

        calcGradeButton.setOnClickListener((View) -> {
            q1 = String.valueOf(spinnerQ1.getSelectedItem());
            q2 = String.valueOf(spinnerQ2.getSelectedItem());
            q3 = String.valueOf(spinnerQ3.getSelectedItem());
            q4 = String.valueOf(spinnerQ4.getSelectedItem());
            mt = String.valueOf(spinnerMT.getSelectedItem());
            fn = String.valueOf(spinnerFN.getSelectedItem());

            finalGradeLabel.setText(gradeCalculator.getOverallGrade());
            }
        );

        return rootView;
    }

}
