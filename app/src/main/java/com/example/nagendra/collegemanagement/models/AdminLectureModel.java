package com.example.nagendra.collegemanagement.models;

public class AdminLectureModel {

    String subjectname,chapters,nooftests,noofperiods,date,userid;

    public AdminLectureModel() {
    }

    public AdminLectureModel(String subjectname, String chapters, String nooftests, String noofperiods, String date, String userid) {
        this.subjectname = subjectname;
        this.chapters = chapters;
        this.nooftests = nooftests;
        this.noofperiods = noofperiods;
        this.date = date;
        this.userid = userid;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getChapters() {
        return chapters;
    }

    public void setChapters(String chapters) {
        this.chapters = chapters;
    }

    public String getNooftests() {
        return nooftests;
    }

    public void setNooftests(String nooftests) {
        this.nooftests = nooftests;
    }

    public String getNoofperiods() {
        return noofperiods;
    }

    public void setNoofperiods(String noofperiods) {
        this.noofperiods = noofperiods;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
