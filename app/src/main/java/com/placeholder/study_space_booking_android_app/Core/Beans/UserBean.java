package com.placeholder.study_space_booking_android_app.Core.Beans;

public class UserBean {
    private Integer id;
    private Integer credit;
    private String userName;
    private String password;
    private Integer isAdministrator;
    private Integer isBlocked;

    public UserBean(Integer id, Integer credit, String userName,
             String password, Integer isAdministrator, Integer isBlocked) {
        this.id = id;
        this.credit = credit;
        this.userName = userName;
        this.password = password;
        this.isAdministrator = isAdministrator;
        this.isBlocked = isBlocked;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String userName) {
        this.userName = userName;
    }

    public void setRole(Integer isAdministrator) {
        this.isAdministrator = isAdministrator;
    }

    public void setState(Integer state) {
        this.isBlocked = state;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCredit() {
        return credit;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Integer isAdministrator() {
        return isAdministrator;
    }

    public Integer isBlocked() {
        return isBlocked;
    }
}
