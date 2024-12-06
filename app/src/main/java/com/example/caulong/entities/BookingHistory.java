package com.example.caulong.entities;

import java.util.List;

public class BookingHistory {
    private long booking_id;

    public int getCourt_id() {
        return court_id;
    }

    public void setCourt_id(int court_id) {
        this.court_id = court_id;
    }

    private int court_id;
    private String courtName;
    private String presentdate;
    private String bookingDate;
    private String status;
    private List<String> times;

    private int customerId;
    private String customerName;
    private double totalTime;
    private double totalItem;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public double getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(double totalItem) {
        this.totalItem = totalItem;
    }

    public BookingHistory(long booking_id, int customerId, String customerName,
                          double totalTime, double totalItem, List<String> times,
                          String status, String presentdate, String bookingDate, int court_id) {
        this.booking_id = booking_id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalTime = totalTime;
        this.totalItem = totalItem;
        this.times = times;
        this.status = status;
        this.presentdate = presentdate;
        this.bookingDate = bookingDate;
        this.court_id = court_id;
    }

    public BookingHistory(long booking_id, String courtName, String presentdate, String bookingDate, String status, List<String> times) {
        this.booking_id = booking_id;
        this.bookingDate = bookingDate;
        this.courtName = courtName;
        this.presentdate = presentdate;
        this.status = status;
        this.times = times;
    }

    public BookingHistory(long booking_id, String courtName, String presentdate, String bookingDate) {
        this.booking_id = booking_id;
        this.courtName = courtName;
        this.presentdate = presentdate;
        this.bookingDate = bookingDate;
    }

    public double getTotalPrice() {
        return totalTime + totalItem;
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

