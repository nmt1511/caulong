package com.example.caulong; // Thay đổi theo package của bạn

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Setup_yard extends AppCompatActivity {

    private EditText editTextDate;
    private ImageView ivCalendarIcon;
    private FrameLayout[] fields;
    private Button btnXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setup_yard);

//        // Liên kết các thành phần từ XML
        editTextDate = findViewById(R.id.editTextDate);
        ivCalendarIcon = findViewById(R.id.ivCalendarIcon);
//        btnXacNhan = findViewById(R.id.btnXacNhan);
//
//        // Các sân bóng
        fields = new FrameLayout[]{
                findViewById(R.id.img1),
                findViewById(R.id.img2),
                findViewById(R.id.img3),
                findViewById(R.id.img4),
                findViewById(R.id.img5),
                findViewById(R.id.img6),
                findViewById(R.id.img7),
                findViewById(R.id.img8),
                findViewById(R.id.img9),
                findViewById(R.id.img10)
        };
//
        // Xử lý sự kiện chọn ngày
        ivCalendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
//
//        // Thiết lập sự kiện click cho từng sân
//        for (FrameLayout field : fields) {
//            field.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    selectField(view);
//                }
//            });
//        }
//
//        // Xử lý sự kiện xác nhận
//        btnXacNhan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                confirmSelection();
//            }
//        });
    }

    // Hàm hiển thị DatePicker để chọn ngày
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Setup_yard.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    // Hàm để chọn sân bóng
    private void selectField(View view) {
        for (FrameLayout field : fields) {
            field.setBackgroundColor(Color.TRANSPARENT);  // Reset lại màu nền cho tất cả các sân
        }
        view.setBackgroundColor(Color.YELLOW);  // Đổi màu nền cho sân được chọn
    }

    // Hàm xử lý khi nhấn nút Xác nhận
    private void confirmSelection() {
        String selectedDate = editTextDate.getText().toString();
        if (selectedDate.isEmpty()) {
            Toast.makeText(Setup_yard.this, "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isFieldSelected = false;
        for (FrameLayout field : fields) {
            if (field.getBackground() != null && ((ColorDrawable) field.getBackground()).getColor() == Color.YELLOW) {
                isFieldSelected = true;
                break;
            }
        }

        if (!isFieldSelected) {
            Toast.makeText(Setup_yard.this, "Vui lòng chọn sân bóng", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Setup_yard.this, "Xác nhận thành công!", Toast.LENGTH_SHORT).show();
        }
    }
}
