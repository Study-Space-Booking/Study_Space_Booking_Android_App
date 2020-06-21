package com.placeholder.study_space_booking_android_app.Core.Beans;

public class TimeSlot {
    Integer id;
    Integer placeId;
    Integer seatId;
    Integer UserId;
    Integer bookStartTime;
    Integer bookEndTime;
    Integer inTime;
    Integer outTime;
    Integer tempLeaveTime;
    Integer tempBackTime;
    Integer state;
    String key;
    String userName;

    public TimeSlot() {}
    public TimeSlot(Integer id, Integer placeId, Integer seatId, Integer userId, Integer bookStartTime,
                            Integer bookEndTime, Integer inTime, Integer outTime, Integer tempLeaveTime,
                            Integer tempBackTime, Integer state) {
        this.id = id;
        this.placeId = placeId;
        this.seatId = seatId;
        UserId = userId;
        this.bookStartTime = bookStartTime;
        this.bookEndTime = bookEndTime;
        this.inTime = inTime;
        this.outTime = outTime;
        this.tempLeaveTime = tempLeaveTime;
        this.tempBackTime = tempBackTime;
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getBookStartTime() {
        return bookStartTime;
    }

    public void setBookStartTime(Integer bookStartTime) {
        this.bookStartTime = bookStartTime;
    }

    public Integer getBookEndTime() {
        return bookEndTime;
    }

    public void setBookEndTime(Integer bookEndTime) {
        this.bookEndTime = bookEndTime;
    }

    public Integer getInTime() {
        return inTime;
    }

    public void setInTime(Integer inTime) {
        this.inTime = inTime;
    }

    public Integer getOutTime() {
        return outTime;
    }

    public void setOutTime(Integer outTime) {
        this.outTime = outTime;
    }

    public Integer getTempLeaveTime() {
        return tempLeaveTime;
    }

    public void setTempLeaveTime(Integer tempLeaveTime) {
        this.tempLeaveTime = tempLeaveTime;
    }

    public Integer getTempBackTime() {
        return tempBackTime;
    }

    public void setTempBackTime(Integer tempBackTime) {
        this.tempBackTime = tempBackTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
