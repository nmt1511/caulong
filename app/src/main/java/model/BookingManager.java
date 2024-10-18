package model;

import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private List<Booking> bookings; // Danh sách các booking

    public BookingManager() {
        bookings = new ArrayList<>();
    }

    // Thêm booking mới
    public boolean addBooking(Booking booking) {
        // Kiểm tra xem có trùng lặp không
        if (isBookingExists(booking.getDate(), booking.getField(), booking.getTime())) {
            return false; // Booking đã tồn tại
        }
        bookings.add(booking);
        return true; // Thêm thành công
    }

    // Kiểm tra xem booking có tồn tại không
    public boolean isBookingExists(String date, String field, String time) {
        for (Booking booking : bookings) {
            if (booking.getDate().equals(date) &&
                    booking.getField().equals(field) &&
                    booking.getTime().equals(time)) {
                return true; // Tồn tại booking
            }
        }
        return false; // Không tồn tại
    }

    // Lấy danh sách tất cả booking
    public List<Booking> getBookings() {
        return bookings;
    }
    public boolean isTimeSlotAvailable(String date, String field, String time) {
        for (Booking booking : bookings) {
            if (booking.getDate().equals(date) &&
                    booking.getField().equals(field) &&
                    booking.getTime().equals(time)) {
                return false; // Nếu có booking trùng khung giờ
            }
        }
        return true; // Nếu không có booking trùng khung giờ
    }

}
