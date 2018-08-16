package com.syosseths.shsmobile;

public class GradeCalculator {

    private final static double
            QUARTER_PERCENT = 0.20,
            MIDTERM_PERCENT = 0.08,
            FINAL_PERCENT = 0.12;

    public static LetterGrade calcGrade(LetterGrade q1, LetterGrade q2, LetterGrade q3, LetterGrade q4, LetterGrade mt, LetterGrade fn) {
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


    public enum LetterGrade {
        A_PLUS(8), A(7), B_PLUS(6), B(5), C_PLUS(4), C(3), D(2), F(0);

        private final double value, roundUpValue;

        LetterGrade(double val) {
            this.value = val;
            this.roundUpValue = val - 0.5;
        }
    }
}
