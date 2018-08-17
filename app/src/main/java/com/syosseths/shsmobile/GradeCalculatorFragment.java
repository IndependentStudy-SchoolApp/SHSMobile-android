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

    enum LetterGrade {
        A_PLUS(8), A(7), B_PLUS(6), B(5), C_PLUS(4), C(3), D(2), F(0);

        private final double value, roundUpValue;

        LetterGrade(double val) {
            this.value = val;
            this.roundUpValue = val - 0.5;
        }

        @Override
        public String toString() {
            return super.toString().replaceAll("_PLUS", "+");
        }

    }

    static class GradeCalculator {

        private final static double
                QUARTER_PERCENT = 0.20,
                MIDTERM_PERCENT = 0.08,
                FINAL_PERCENT = 0.12;

        static LetterGrade calcGrade(LetterGrade q1, LetterGrade q2, LetterGrade q3, LetterGrade q4, LetterGrade mt, LetterGrade fn) {
            double grade =
                    (getNumberGrade(q1) * QUARTER_PERCENT)
                            + (getNumberGrade(q2) * QUARTER_PERCENT)
                            + (getNumberGrade(q3) * QUARTER_PERCENT)
                            + (getNumberGrade(q4) * QUARTER_PERCENT)
                            + (getNumberGrade(mt) * MIDTERM_PERCENT)
                            + (getNumberGrade(fn) * FINAL_PERCENT);

            return getLetterGrade(grade);
        }

        private static LetterGrade getLetterGrade(double grade) {

            for (LetterGrade letterGrade : LetterGrade.values()) {
                if (grade >= letterGrade.roundUpValue) return letterGrade;
            }

            return LetterGrade.F;
        }

        private static double getNumberGrade(LetterGrade letterGrade) {
            return letterGrade.value;
        }
    }
}