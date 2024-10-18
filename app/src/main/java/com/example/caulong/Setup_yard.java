package com.example.caulong; // Thay đổi theo package của bạn

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import model.Booking;
import model.BookingManager;

public class Setup_yard extends Fragment {

    private BookingManager bookingManager;
    private EditText editTextDate;
    private Button btnXacNhan;
    private GridLayout gridLayout;

    // Thay đổi theo các nút thời gian bạn đã tạo trong layout
    private Button btnTime9;
    private Button btnTime10;
    private Button btnTime11;
    private Button btnTime13;
    private Button btnTime14;
    private Button btnTime15;
    private Button btnTime16;
    private Button btnTime17;
    private Button btnTime18;

    private String selectedTime;
    private String selectedField;

    public Setup_yard() {
        // Initialize BookingManager
        bookingManager = new BookingManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_yard, container, false);

        // Liên kết các thành phần giao diện
        editTextDate = view.findViewById(R.id.editTextDate);
        btnXacNhan = view.findViewById(R.id.btnXacNhan);
        gridLayout = view.findViewById(R.id.gridLayout);

        // Khai báo các nút thời gian
        btnTime9 = view.findViewById(R.id.btnTime9);
        btnTime10 = view.findViewById(R.id.btnTime10);
        btnTime11 = view.findViewById(R.id.btnTime11);
        btnTime13 = view.findViewById(R.id.btnTime13);
        btnTime14 = view.findViewById(R.id.btnTime14);
        btnTime15 = view.findViewById(R.id.btnTime15);
        btnTime16 = view.findViewById(R.id.btnTime16);
        btnTime17 = view.findViewById(R.id.btnTime17);
        btnTime18 = view.findViewById(R.id.btnTime18);

        // Thêm các nút thời gian khác ở đây

        // Thiết lập sự kiện cho nút xác nhận
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = editTextDate.getText().toString();
                String time = getSelectedTime(); // Hàm để lấy giờ được chọn
                String field = getSelectedField(); // Hàm để lấy sân được chọn

                // Kiểm tra xem thời gian có khả dụng không
                if (bookingManager.isTimeSlotAvailable(date, field, time)) {
                    // Nếu có, lưu thông tin và thêm vào danh sách
                    bookingManager.addBooking(new Booking(date, field, time));
                    Toast.makeText(getActivity(), "Đặt sân thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu không, thông báo người dùng
                    Toast.makeText(getActivity(), "Khung giờ này đã có người đặt!", Toast.LENGTH_SHORT).show();
                    disableTimeButton(time); // Hàm để làm tối các nút giờ đã đặt
                }
            }
        });

        return view;
    }

    private String getSelectedTime() {
        // Kiểm tra các nút thời gian để xác định khung giờ được chọn
        if (btnTime9.isPressed()) {
            selectedTime = "09:00";
        } else if (btnTime10.isPressed()) {
            selectedTime = "10:00";
        } else if (btnTime11.isPressed()) {
            selectedTime = "11:00";
        } else if (btnTime13.isPressed()) {
            selectedTime = "12:00";
        } else if (btnTime13.isPressed()) {
            selectedTime = "13:00";
        } else if (btnTime14.isPressed()) {
            selectedTime = "14:00";
        } else if (btnTime15.isPressed()) {
            selectedTime = "15:00";
        } else if (btnTime16.isPressed()) {
            selectedTime = "16:00";
        } else if (btnTime17.isPressed()) {
            selectedTime = "17:00";
        } else if (btnTime18.isPressed()) {
            selectedTime = "18:00";
        } else {
            selectedTime = ""; // Nếu không có nút nào được chọn
        }
        return selectedTime;
    }


    private String getSelectedField() {
        // Kiểm tra từng ImageView hoặc Button trong gridLayout để xác định sân nào đã được chọn
        // Giả sử bạn có các nút hoặc hình ảnh đại diện cho các sân
        // Trả về giá trị số sân (ví dụ: "1", "2", ...).
        // Ví dụ: return "1";
        return selectedField;
    }

    private void disableTimeButton(String time) {
        // Duyệt qua tất cả các nút giờ và làm tối nút tương ứng với giờ đã đặt
        switch (time) {
            case "09:00":
                btnTime9.setEnabled(false);
                btnTime9.setAlpha(0.5f); // Làm tối nút
                break;
            case "10:00":
                btnTime10.setEnabled(false);
                btnTime10.setAlpha(0.5f);
                break;
            case "11:00":
                btnTime11.setEnabled(false);
                btnTime11.setAlpha(0.5f);
                break;
            case "13:00":
                btnTime13.setEnabled(false);
                btnTime13.setAlpha(0.5f);
                break;
            case "14:00":
                btnTime14.setEnabled(false);
                btnTime14.setAlpha(0.5f);
                break;
            case "15:00":
                btnTime15.setEnabled(false);
                btnTime15.setAlpha(0.5f);
                break;
            case "16:00":
                btnTime16.setEnabled(false);
                btnTime16.setAlpha(0.5f);
                break;
            case "17:00":
                btnTime17.setEnabled(false);
                btnTime17.setAlpha(0.5f);
                break;
            case "18:00":
                btnTime18.setEnabled(false);
                btnTime18.setAlpha(0.5f);
                break;
            default:
                // Không có hành động gì nếu thời gian không khớp
                break;
        }
    }

}
