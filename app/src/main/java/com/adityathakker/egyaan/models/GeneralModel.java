package com.adityathakker.egyaan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 26/12/16.
 */

public class GeneralModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("details")
    @Expose
    private Details details;
    @SerializedName("timetable")
    @Expose
    private List<TimetableData> timetableDataList = null;
    @SerializedName("course")
    @Expose
    private List<CourseDataModel> courseDataModels = null;
    @SerializedName("notes")
    @Expose
    private List<NotesModel> notes = null;
    @SerializedName("tests")
    @Expose
    private List<TestsModel> tests = null;
    @SerializedName("test")
    @Expose
    private List<TestsDetailsModel> testDetails = null;
    @SerializedName("notice")
    @Expose
    private List<NoticeboardModel> noticeboard = null;
    @SerializedName("fullNotice")
    @Expose
    private List<FullNoticeboardViewModel> fullNotice = null;

//    public GeneralModel(String status, Details details) {
//        this.status = status;
//        this.details = details;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public List<TimetableData> getTimetableDataList() {
        return timetableDataList;
    }

    public void setTimetableDataList(List<TimetableData> timetableDataList) {
        this.timetableDataList = timetableDataList;
    }

    public List<CourseDataModel> getCourseDataModels() {
        return courseDataModels;
    }

    public void setCourseDataModels(List<CourseDataModel> courseDataModels) {
        this.courseDataModels = courseDataModels;
    }

    public List<NotesModel> getNotes() {
        return notes;
    }

    public void setNotes(List<NotesModel> notes) {
        this.notes = notes;
    }

    public List<TestsModel> getTests() {
        return tests;
    }

    public void setTests(List<TestsModel> tests) {
        this.tests = tests;
    }

    public List<TestsDetailsModel> getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(List<TestsDetailsModel> testDetails) {
        this.testDetails = testDetails;
    }

    public List<NoticeboardModel> getNoticeboard() {
        return noticeboard;
    }

    public void setNoticeboard(List<NoticeboardModel> noticeboard) {
        this.noticeboard = noticeboard;
    }

    public List<FullNoticeboardViewModel> getFullNotice() {
        return fullNotice;
    }

    public void setFullNotice(List<FullNoticeboardViewModel> fullNotice) {
        this.fullNotice = fullNotice;
    }
}
