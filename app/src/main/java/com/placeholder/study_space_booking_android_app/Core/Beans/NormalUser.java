package com.placeholder.study_space_booking_android_app.Core.Beans;

public class NormalUser extends User{
    //private Integer id;
    private Integer credit;
    //private String userName;
    //private String password;
    //private Integer isAdministrator;
    private Integer blocked;

    public NormalUser() {

    }

    public NormalUser(Integer id, Integer credit, String userName,
             String password, Integer blocked) {
        super(id, userName, password);
        this.credit = credit;

        //this.isAdministrator = isAdministrator;
        this.blocked = blocked;
    }


    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    //public void setRole(Integer isAdministrator) {
    //    this.isAdministrator = isAdministrator;
    //}

    public void setState(Integer state) {
        this.blocked = state;
    }

    public Integer getCredit() {
        return credit;
    }

    //public Integer isAdministrator() {
    //    return isAdministrator;
    //}

    public Integer isBlocked() {
        return blocked;
    }
}
