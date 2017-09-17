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
}
