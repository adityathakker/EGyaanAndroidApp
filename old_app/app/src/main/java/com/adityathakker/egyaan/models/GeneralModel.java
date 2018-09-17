package com.adityathakker.egyaan.models;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 26/12/16.
 */

public class GeneralModel {
    private String status;
    private String message;

    public GeneralModel(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
