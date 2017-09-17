package com.adityathakker.egyaan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fireion on 27/6/17.
 */

public class Details {

    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("student_passwd")
    @Expose
    private String studentPasswd;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("student_profile_photo")
    @Expose
    private String studentProfilePhoto;
    @SerializedName("parent_profile_photo")
    @Expose
    private String parentProfilePhoto;
    @SerializedName("batch_id")
    @Expose
    private String batchId;
    @SerializedName("branch_id")
    @Expose
    private String branchId;
    @SerializedName("parent_name")
    @Expose
    private String parentName;
    @SerializedName("parent_email")
    @Expose
    private String parentEmail;
    @SerializedName("parent_passwd")
    @Expose
    private String parentPasswd;
    @SerializedName("parent_mobile")
    @Expose
    private String parentMobile;

    /**
     * No args constructor for use in serialization
     */
    public Details() {
    }

    /**
     * @param studentProfilePhoto
     * @param studentPasswd
     * @param parentMobile
     * @param lastname
     * @param batchId
     * @param firstname
     * @param parentPasswd
     * @param parentEmail
     * @param email
     * @param branchId
     * @param userId
     * @param parentProfilePhoto
     * @param gender
     * @param parentName
     * @param roleId
     * @param mobile
     */
    public Details(Integer roleId, String userId, String firstname, String lastname, String email,
                   String studentPasswd, String gender, String mobile, String studentProfilePhoto,
                   String parentProfilePhoto, String batchId, String branchId, String parentName,
                   String parentEmail, String parentPasswd, String parentMobile) {
        super();
        this.roleId = roleId;
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.studentPasswd = studentPasswd;
        this.gender = gender;
        this.mobile = mobile;
        this.studentProfilePhoto = studentProfilePhoto;
        this.parentProfilePhoto = parentProfilePhoto;
        this.batchId = batchId;
        this.branchId = branchId;
        this.parentName = parentName;
        this.parentEmail = parentEmail;
        this.parentPasswd = parentPasswd;
        this.parentMobile = parentMobile;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentPasswd() {
        return studentPasswd;
    }

    public void setStudentPasswd(String studentPasswd) {
        this.studentPasswd = studentPasswd;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStudentProfilePhoto() {
        return studentProfilePhoto;
    }

    public void setStudentProfilePhoto(String studentProfilePhoto) {
        this.studentProfilePhoto = studentProfilePhoto;
    }

    public String getParentProfilePhoto() {
        return parentProfilePhoto;
    }

    public void setParentProfilePhoto(String parentProfilePhoto) {
        this.parentProfilePhoto = parentProfilePhoto;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getParentPasswd() {
        return parentPasswd;
    }

    public void setParentPasswd(String parentPasswd) {
        this.parentPasswd = parentPasswd;
    }

    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }

}