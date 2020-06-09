package com.example.foodfreak.com.foodfreak.main.user;

public class HelperClass {
    String fullname_hc,amizone_id_hc,mobile_num_hc,email_id_hc;
    public HelperClass()
    {

    }

    public HelperClass(String fullname_hc, String amizone_id_hc, String mobile_num_hc, String email_id_hc) {
        this.fullname_hc = fullname_hc;
        this.amizone_id_hc = amizone_id_hc;
        this.mobile_num_hc = mobile_num_hc;
        this.email_id_hc = email_id_hc;
    }

    public String getFullname_hc() {
        return fullname_hc;
    }

    public void setFullname_hc(String fullname_hc) {
        this.fullname_hc = fullname_hc;
    }

    public String getAmizone_id_hc() {
        return amizone_id_hc;
    }

    public void setAmizone_id_hc(String amizone_id_hc) {
        this.amizone_id_hc = amizone_id_hc;
    }

    public String getMobile_num_hc() {
        return mobile_num_hc;
    }

    public void setMobile_num_hc(String mobile_num_hc) {
        this.mobile_num_hc = mobile_num_hc;
    }

    public String getEmail_id_hc() {
        return email_id_hc;
    }

    public void setEmail_id_hc(String email_id_hc) {
        this.email_id_hc = email_id_hc;
    }


}
