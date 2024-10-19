package com.example.caulong.menubottom;

public class BookingHistory {
    private String courtName;
    private String date;
    private String time;
    private String status;

    public BookingHistory(String courtName, String date, String time, String status) {
        this.courtName = courtName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getCourtName() {
        return courtName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
