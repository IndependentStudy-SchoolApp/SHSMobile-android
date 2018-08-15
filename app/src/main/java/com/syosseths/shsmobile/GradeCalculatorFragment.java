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

    String q1 = "F", q2 = "F", q3 = "F", q4 = "F", mt = "F", fn = "F";
    TextView finalGradeLabel;
    Spinner etq1, etq2, etq3, etq4, etmt, etfn;
    Button calcButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grade_calculator, container, false);

        finalGradeLabel = rootView.findViewById(R.id.finalGradeText);
        etq1 = rootView.findViewById(R.id.spinnerQ1);
        etq2 = rootView.findViewById(R.id.spinnerQ2);
        etq3 = rootView.findViewById(R.id.spinnerQ3);
        etq4 = rootView.findViewById(R.id.spinnerQ4);
        etmt = rootView.findViewById(R.id.spinnerMT);
        etfn = rootView.findViewById(R.id.spinnerFN);
        calcButton = rootView.findViewById(R.id.calcGradeButton);


        View v = new View(getContext());
        calcButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                q1 = String.valueOf(etq1.getSelectedItem());
                q2 = String.valueOf(etq2.getSelectedItem());
                q3 = String.valueOf(etq3.getSelectedItem());
                q4 = String.valueOf(etq4.getSelectedItem());
                mt = String.valueOf(etmt.getSelectedItem());
                fn = String.valueOf(etfn.getSelectedItem());

                FinalGradeCalc fgc = new FinalGradeCalc(q1, q2, q3, q4, mt, fn);

                finalGradeLabel.setText(fgc.getOverallGrade());
            }
        });

        return rootView;
    }

}
