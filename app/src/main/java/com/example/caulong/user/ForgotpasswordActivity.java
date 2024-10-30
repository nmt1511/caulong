package com.example.caulong.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.R;

public class ForgotpasswordActivity extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnResetPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        edtEmail = findViewById(R.id.edtEmail);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(ForgotpasswordActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý yêu cầu đặt lại mật khẩu
                    Toast.makeText(ForgotpasswordActivity.this, "Đã gửi yêu cầu đặt lại mật khẩu đến email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
