package com.androidsphere.aditya.egyaan.model;

/**
 * Created by aditya9172 on 19/1/16.
 */
public class Notice {
    String uid;

    public String getUid() {
        return uid;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getNotice() {
        return notice;
    }

    public String getFile() {
        return file;
    }

    String id;
    String type;
    String title;
    String notice;
    String file;

    public static class Builder {
        private  String title,notice;
        private String uid = null,id = null,type=null,file=null;
        public Builder(String title, String notice){
            this.title = title;
            this.notice = notice;
        }

        public Builder uid(String uid){
            this.uid = uid;
            return this;
        }

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder type(String type){
            this.type = type;
            return this;
        }

        public Builder file(String file){
            this.file = file;
            return this;
        }

        public Notice build(){
            return new Notice(this);
        }

    }

    private Notice(Builder builder){
        uid = builder.uid;
        id = builder.id;
        type = builder.type;
        file = builder.file;
        title = builder.title;
        notice = builder.notice;
    }


}
