package com.example.quizappdmrd;

import java.io.Serializable;

public class QuestionsList implements Serializable {

    private final String Question,Option1,Option2,Option3,Option4;
    private final int answer ;
    private int userSelectedAnswer;

    public QuestionsList(String question, String option1, String option2, String option3, String option4, int answer) {
        Question = question;
        Option1 = option1;
        Option2 = option2;
        Option3 = option3;
        Option4 = option4;
        this.answer = answer;
        this.userSelectedAnswer=0;
    }

    public String getQuestion() {
        return Question;
    }

    public String getOption1() {
        return Option1;
    }

    public String getOption2() {
        return Option2;
    }

    public String getOption3() {
        return Option3;
    }

    public String getOption4() {
        return Option4;
    }

    public int getAnswer() {
        return answer;
    }

    public int getUserSelectedAnswer() {
        return userSelectedAnswer;
    }

    public void setUserSelectedAnswer(int userSelectedAnswer) {
        this.userSelectedAnswer = userSelectedAnswer;
    }
}
