package com.adityathakker.egyaan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fireion on 30/6/17.
 */

public class TimetableData {
    @SerializedName("day_id")
    @Expose
    private String dayId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("comment")
    @Expose
    private String comment;

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
