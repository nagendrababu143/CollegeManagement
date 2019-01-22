package com.example.nagendra.collegemanagement.models;

public class AdminInformation {

    private String adminuid,adminusername,adminemailid,adminpassword,adminphonenumber,admincollegeid,admindepartment;

    public AdminInformation() {
    }

    public AdminInformation(String adminuid, String adminusername, String adminemailid, String adminpassword, String adminphonenumber, String admincollegeid, String admindepartment) {
        this.adminuid = adminuid;
        this.adminusername = adminusername;
        this.adminemailid = adminemailid;
        this.adminpassword = adminpassword;
        this.adminphonenumber = adminphonenumber;
        this.admincollegeid = admincollegeid;
        this.admindepartment = admindepartment;
    }

    public String getAdminuid() {
        return adminuid;
    }

    public void setAdminuid(String adminuid) {
        this.adminuid = adminuid;
    }

    public String getAdminusername() {
        return adminusername;
    }

    public void setAdminusername(String adminusername) {
        this.adminusername = adminusername;
    }

    public String getAdminemailid() {
        return adminemailid;
    }

    public void setAdminemailid(String adminemailid) {
        this.adminemailid = adminemailid;
    }

    public String getAdminpassword() {
        return adminpassword;
    }

    public void setAdminpassword(String adminpassword) {
        this.adminpassword = adminpassword;
    }

    public String getAdminphonenumber() {
        return adminphonenumber;
    }

    public void setAdminphonenumber(String adminphonenumber) {
        this.adminphonenumber = adminphonenumber;
    }

    public String getAdmincollegeid() {
        return admincollegeid;
    }

    public void setAdmincollegeid(String admincollegeid) {
        this.admincollegeid = admincollegeid;
    }

    public String getAdmindepartment() {
        return admindepartment;
    }

    public void setAdmindepartment(String admindepartment) {
        this.admindepartment = admindepartment;
    }
}
