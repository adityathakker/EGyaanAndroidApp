package com.adityathakker.egyaan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fireion on 30/11/17.
 */

public class TestsDetailsModel {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("correct_ans")
    @Expose
    private String correctAns;
    @SerializedName("answer_text")
    @Expose
    private String answerText;
    @SerializedName("correct_ans_text")
    @Expose
    private String correctAnsText;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getCorrectAnsText() {
        return correctAnsText;
    }

    public void setCorrectAnsText(String correctAnsText) {
        this.correctAnsText = correctAnsText;
    }

}