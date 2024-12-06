package com.example.caulong.admin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Customer;
import com.example.caulong.entities.tournament;
import com.example.caulong.user.LoginActivity;
import com.example.caulong.user.SignupActivity;

public class InsertCustomerActivity extends Activity {

    private EditText edtName, edtPhone, edtEmail, edtUsername, edtPass, edtConfirmPass;
    private RadioGroup rdgGender;
    private RadioButton rdNam, rdNu;
    private Button btnAdd;
    private DataDatSan helper;
    SQLiteDatabase db;

    void init(){
        edtName = findViewById(R.id.edt_iname);
        edtPhone = findViewById(R.id.edt_iphone);
        edtEmail = findViewById(R.id.edt_iemail);
        edtUsername = findViewById(R.id.edt_iusername);
        edtPass = findViewById(R.id.edt_ipass);
        edtConfirmPass = findViewById(R.id.edt_ipasscomfirm);
        rdgGender = findViewById(R.id.rd_igroupGender);
        rdNam = findViewById(R.id.rd_inam);
        rdNu = findViewById(R.id.rd_inu);
        btnAdd = findViewById(R.id.btn_iAddCustomer);
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
        String password = edtPass.getText().toString();
        if (password.isEmpty()) {
            edtPass.setError("Vui lòng nhập mật khẩu");
            edtPass.requestFocus();
            return false;
        } else if (password.length() < 6) { // Kiểm tra độ dài mật khẩu
            edtPass.setError("Mật khẩu phải có ít nhất 6 ký tự");
            edtPass.requestFocus();
            return false;
        }

        // Kiểm tra xác nhận mật khẩu
        String confirmPassword = edtConfirmPass.getText().toString();
        if (!confirmPassword.equals(password)) {
            edtConfirmPass.setError("Mật khẩu xác nhận không khớp");
            edtConfirmPass.requestFocus();
            return false;
        }

        // Kiểm tra trường giới tính
        if (rdgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // Nếu tất cả các trường hợp đều hợp lệ
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer);
        init();
        rdNam.setChecked(true);
        helper = new DataDatSan(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddCustomer();
            }
        });
    }
    private void handleAddCustomer() {
        db = helper.getReadableDatabase();
        if (validateInput()) {
            // Lấy giá trị từ các trường đầu vào
            String username = edtUsername.getText().toString().trim();
            String password = edtPass.getText().toString();
            String role = "guest"; // Gán vai trò mặc định là 'guest'

            // Kiểm tra xem username đã tồn tại chưa
            Cursor cursor = db.rawQuery("SELECT * FROM User WHERE username = ?", new String[]{username});
            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Tài khoản đã tồn tại, vui lòng chọn tên khác", Toast.LENGTH_SHORT).show();
                cursor.close();
            } else {
                cursor.close();
                ContentValues userValues = new ContentValues();
                userValues.put("username", username);
                userValues.put("password", password);
                userValues.put("role", role);
                String name = edtName.getText().toString().trim();
                String gender = rdNam.isChecked() ? "Nam" : "Nữ";
                String phone = edtPhone.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                long userId = db.insert("User", null, userValues);

                if (userId != -1) { // Kiểm tra thêm vào User thành công
                    long id = saveCustomer(userId);
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    if(id != -1){
                        Customer customer = new Customer(id+" ", name, gender, phone, email,userId,"");
                        bundle.putSerializable("customer",customer);
                        intent.putExtra("data",bundle);
                        setResult(Customer_List.SAVE_CUSTOMER, intent);
                        Toast.makeText(getApplication(), "Thêm khách hàng thành công!!!", Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        // Xóa dữ liệu user nếu thêm vào Customer thất bại
                        db.delete("User", "user_id = ?", new String[]{String.valueOf(userId)});
                        Toast.makeText(this, "Lỗi khi thêm người dùng, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Lỗi khi thêm người dùng, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    private long saveCustomer(long userId){
        db = helper.getWritableDatabase();
        String name = edtName.getText().toString().trim();
        String gender = rdNam.isChecked() ? "Nam" : "Nữ";
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        try{
            ContentValues customerValues = new ContentValues();
            customerValues.put("customer_name", name);
            customerValues.put("gender", gender);
            customerValues.put("phone_number", phone);
            customerValues.put("email", email);
            customerValues.put("user_id", userId);
            return (db.insert("Customer",null,customerValues));
        }catch (Exception e){
            Toast.makeText(getApplication(), "Lỗi "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return -1;
    }
}
