package com.syosseths.shsmobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.syosseths.shsmobile.GradeCalculator.LetterGrade;

import java.util.Objects;

public class GradeCalculatorFragment extends Fragment {

    private LetterGrade q1, q2, q3, q4, mt, fn;
    private TextView finalGradeLabel;
    private Spinner spinnerQ1, spinnerQ2, spinnerQ3, spinnerQ4, spinnerMT, spinnerFN;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grade_calculator, container, false);

        finalGradeLabel = rootView.findViewById(R.id.finalGradeLabel);

        Spinner[] spinners = new Spinner[6];
        spinners[0] = (spinnerQ1 = rootView.findViewById(R.id.q1_spinner));
        spinners[1] = (spinnerQ2 = rootView.findViewById(R.id.q2_spinner));
        spinners[2] = (spinnerQ3 = rootView.findViewById(R.id.q3_spinner));
        spinners[3] = (spinnerQ4 = rootView.findViewById(R.id.q4_spinner));
        spinners[4] = (spinnerMT = rootView.findViewById(R.id.mt_spinner));
        spinners[5] = (spinnerFN = rootView.findViewById(R.id.fn_spinner));
        for (Spinner spinner : spinners) {
            spinner.setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, LetterGrade.values()));
        }

        Button calculateGradeButton = rootView.findViewById(R.id.calculateGradeButton);

        calculateGradeButton.setOnClickListener((View) -> {
            q1 = (LetterGrade) spinnerQ1.getSelectedItem();
            q2 = (LetterGrade) spinnerQ2.getSelectedItem();
            q3 = (LetterGrade) spinnerQ3.getSelectedItem();
            q4 = (LetterGrade) spinnerQ4.getSelectedItem();
            mt = (LetterGrade) spinnerMT.getSelectedItem();
            fn = (LetterGrade) spinnerFN.getSelectedItem();

            finalGradeLabel.setText(GradeCalculator.calcGrade(q1, q2, q3, q4, mt, fn).toString());
            }
        );

        return rootView;
    }

}
