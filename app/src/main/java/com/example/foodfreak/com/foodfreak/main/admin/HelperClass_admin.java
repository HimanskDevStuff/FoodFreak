package com.example.foodfreak.com.foodfreak.main.admin;


public class HelperClass_admin {
    String manager_name,manager_contact_num,working_id,canteen_name,manager_email_id;
    HelperClass_admin()
    {

    }

    public HelperClass_admin(String manager_name, String manager_contact_num, String working_id, String canteen_name, String manager_email_id) {
        this.manager_name = manager_name;
        this.manager_contact_num = manager_contact_num;
        this.working_id = working_id;
        this.canteen_name = canteen_name;
        this.manager_email_id = manager_email_id;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getManager_contact_num() {
        return manager_contact_num;
    }

    public void setManager_contact_num(String manager_contact_num) {
        this.manager_contact_num = manager_contact_num;
    }

    public String getWorking_id() {
        return working_id;
    }

    public void setWorking_id(String working_id) {
        this.working_id = working_id;
    }

    public String getCanteen_name() {
        return canteen_name;
    }

    public void setCanteen_name(String canteen_name) {
        this.canteen_name = canteen_name;
    }

    public String getManager_email_id() {
        return manager_email_id;
    }

    public void setManager_email_id(String manager_email_id) {
        this.manager_email_id = manager_email_id;
    }
}
