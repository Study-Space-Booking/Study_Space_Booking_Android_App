package com.placeholder.study_space_booking_android_app.Core.Beans;

public class ProbRepo {
    Integer id;
    String title;
    Integer placeId;
    Integer seatId;
    String description;
    String image;
    Integer time;
    Integer reporterId;
    String remarks;

    public ProbRepo(Integer id, String title, Integer placeId, Integer seatId, String description,
                            String image, Integer time, Integer reporterId, String remarks) {
        this.id = id;
        this.title = title;
        this.placeId = placeId;
        this.seatId = seatId;
        this.description = description;
        this.image = image;
        this.time = time;
        this.reporterId = reporterId;
        this.remarks = remarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getReporterId() {
        return reporterId;
    }

    public void setReporterId(Integer reporterId) {
        this.reporterId = reporterId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }




}
