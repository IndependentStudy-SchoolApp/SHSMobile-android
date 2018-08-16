package com.syosseths.shsmobile;

import android.util.Log;

public class GradeCalculator {

    private String Q1grade;
    private String Q2grade;
    private String Q3grade;
    private String Q4grade;
    private String midtermGrade;
    private String finalGrade;


    GradeCalculator(String q1, String q2, String q3, String q4, String mt, String fn) {
        Q1grade = q1;
        Q2grade = q2;
        Q3grade = q3;
        Q4grade = q4;
        midtermGrade = mt;
        finalGrade = fn;
    }

    public String getOverallGrade() {
        double gradeNum;

        gradeNum = (getNum(Q1grade) * 0.2) + (getNum(Q2grade) * 0.2) + (getNum(Q3grade) * 0.2)
                + (getNum(Q4grade) * 0.2) + (getNum(midtermGrade) * .08) + (getNum(finalGrade) * .12);

        Log.d("FINAL CLASS GRADE: ", String.valueOf(gradeNum));

        return getLetter(gradeNum);
    }


    private static String getLetter(double grade) {
        if (grade >= 7.5)
            return "A+";
        else if (grade >= 6.5)
            return "A";
        else if (grade >= 5.5)
            return "B+";
        else if (grade >= 4.5)
            return "B";
        else if (grade >= 3.5)
            return "C+";
        else if (grade >= 2.5)
            return "C";
        else if (grade >= 1.5)
            return "D";
        else
            return "F";
    }


    private static double getNum(String letter) {
        switch (letter) {
            case "A+":
                return 8;
            case "A":
                return 7;
            case "B+":
                return 6;
            case "B":
                return 5;
            case "C+":
                return 4;
            case "C":
                return 3;
            case "D":
                return 2;
            case "F":
                return 0;
        }
        return 0;
    }
}
