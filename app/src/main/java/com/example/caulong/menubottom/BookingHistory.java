package com.example.caulong.menubottom;

import java.util.List;

public class BookingHistory {
    private long booking_id;
    private String courtName;
    private String presentdate;
    private String bookingDate;
    private String status;
    private List<String> times;

    public BookingHistory(long booking_id, String courtName, String presentdate, String bookingDate, String status, List<String> times) {
        this.booking_id = booking_id;
        this.bookingDate = bookingDate;
        this.courtName = courtName;
        this.presentdate = presentdate;
        this.status = status;
        this.times = times;
    }

    public long getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(long booking_id) {
        this.booking_id = booking_id;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getPresentdate() {
        return presentdate;
    }

    public void setPresentdate(String presentdate) {
        this.presentdate = presentdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }
}
