package com.example.caulong.user;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.data.DataDatSan;
import com.example.caulong.R;

public class SignupActivity extends AppCompatActivity{
    private SQLiteDatabase db;
    DataDatSan dbDatSan;
    private EditText edtName, edtPhone, edtEmail, edtUsername, edtPassword, edtConfirmPassword;
    private RadioGroup rdgGender;
    private RadioButton rdNam, rdNu;
    private Button btnRegister ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dbDatSan = new DataDatSan(this);
        db = dbDatSan.getWritableDatabase();
        init();
        rdNam.setChecked(true);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Đóng Activity hiện tại để ngăn người dùng quay lại trang đăng ký bằng nút Back
            }
        });
    }

    private void init() {
        // Liên kết các thành phần trong XML với đối tượng Java
        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_pass);
        edtConfirmPassword = findViewById(R.id.edt_passcomfirm);

        // RadioGroup và RadioButton cho giới tính
        rdgGender = findViewById(R.id.rdigroupDegree);
        rdNam = findViewById(R.id.rdnam);
        rdNu = findViewById(R.id.rdnu);

        btnRegister = findViewById(R.id.btn_register);

    }
    private boolean validateInput() {
        // Kiểm tra trường tên
        if (edtName.getText().toString().trim().isEmpty()) {
            edtName.setError("Vui lòng nhập tên");
            edtName.requestFocus();
            return false;
        }

        // Kiểm tra trường số điện thoại
        String phone = edtPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            edtPhone.setError("Vui lòng nhập số điện thoại");
            edtPhone.requestFocus();
            return false;
        } else if (!phone.matches("\\d{10,11}")) { // Kiểm tra số điện thoại 10-11 chữ số
            edtPhone.setError("Số điện thoại không hợp lệ");
            edtPhone.requestFocus();
            return false;
        }

        // Kiểm tra trường email
        String email = edtEmail.getText().toString().trim();
        if (email.isEmpty()) {
            edtEmail.setError("Vui lòng nhập email");
            edtEmail.requestFocus();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // Kiểm tra định dạng email
            edtEmail.setError("Email không hợp lệ");
            edtEmail.requestFocus();
            return false;
        }

        // Kiểm tra trường tài khoản
        if (edtUsername.getText().toString().trim().isEmpty()) {
            edtUsername.setError("Vui lòng nhập tài khoản");
            edtUsername.requestFocus();
            return false;
        }

        // Kiểm tra trường mật khẩu
        String password = edtPassword.getText().toString();
        if (password.isEmpty()) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            edtPassword.requestFocus();
            return false;
        } else if (password.length() < 6) { // Kiểm tra độ dài mật khẩu
            edtPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            edtPassword.requestFocus();
            return false;
        }

        // Kiểm tra xác nhận mật khẩu
        String confirmPassword = edtConfirmPassword.getText().toString();
        if (!confirmPassword.equals(password)) {
            edtConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            edtConfirmPassword.requestFocus();
            return false;
        }

        // Kiểm tra trường giới tính
        if (rdgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // Nếu tất cả các trường hợp đều hợp lệ
    }


        private void handleRegister() {
            // Kiểm tra đầu vào có hợp lệ không
            if (validateInput()) {
                // Lấy giá trị từ các trường đầu vào
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString();
                String role = "guest"; // Gán vai trò mặc định là 'guest'
                String name = edtName.getText().toString().trim();
                String gender = rdNam.isChecked() ? "Nam" : "Nữ";
                String phone = edtPhone.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();

                // Kiểm tra xem username đã tồn tại chưa
                Cursor cursor = db.rawQuery("SELECT * FROM User WHERE username = ?", new String[]{username});
                if (cursor.moveToFirst()) {
                    // Nếu username đã tồn tại, hiển thị thông báo lỗi
                    Toast.makeText(this, "Tài khoản đã tồn tại, vui lòng chọn tên khác", Toast.LENGTH_SHORT).show();
                    cursor.close();
                } else {
                    cursor.close();

                    // Thêm người dùng vào bảng User
                    ContentValues userValues = new ContentValues();
                    userValues.put("username", username);
                    userValues.put("password", password);
                    userValues.put("role", role);
                    long userId = db.insert("User", null, userValues); // Lấy user_id vừa tạo

                    if (userId != -1) { // Kiểm tra thêm vào User thành công
                        // Thêm thông tin khách hàng vào bảng Customer
                        ContentValues customerValues = new ContentValues();
                        customerValues.put("customer_name", name);
                        customerValues.put("gender", gender);
                        customerValues.put("phone_number", phone);
                        customerValues.put("email", email);
                        customerValues.put("user_id", userId);
                        customerValues.put("avatar","");

                        long customerId = db.insert("Customer", null, customerValues); // Thêm vào bảng Customer
                        if (customerId != -1) {
                            // Đăng ký thành công
                            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            // Điều hướng hoặc xóa thông tin nếu cần
                        } else {
                            // Xóa dữ liệu user nếu thêm vào Customer thất bại
                            db.delete("User", "user_id = ?", new String[]{String.valueOf(userId)});
                            Toast.makeText(this, "Lỗi khi đăng ký, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Lỗi khi thêm người dùng, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                }

        }

    }

}
