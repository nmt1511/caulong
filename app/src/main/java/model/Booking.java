package model;

public class Booking {
    private int id;        // ID của đặt sân
    private String date;   // Ngày đặt
    private String field;  // Tên sân
    private String time;   // Thời gian đặt

    // Constructor cho Booking với ID
    public Booking(int id, String date, String field, String time) {
        this.id = id;
        this.date = date;
        this.field = field;
        this.time = time;
    }

    // Constructor cho Booking không có ID (dùng khi thêm đặt sân mới)
    public Booking(String date, String field, String time) {
        this.date = date;
        this.field = field;
        this.time = time;
    }

    // Getter cho các thuộc tính
    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getField() {
        return field;
    }

    public String getTime() {
        return time;
    }
}
