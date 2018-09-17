package com.adityathakker.egyaan.models;

/**
 * Created by fireion on 30/11/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestsModel {

    @SerializedName("test_id")
    @Expose
    private String testId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("date_of_test")
    @Expose
    private String dateOfTest;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("total_marks")
    @Expose
    private String totalMarks;
    @SerializedName("type")
    @Expose
    private String type;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateOfTest() {
        return dateOfTest;
    }

    public void setDateOfTest(String dateOfTest) {
        this.dateOfTest = dateOfTest;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}