package com.example.caulong.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.R;

public class LogoutActivity{
    public static void showExitConfirmationDialog(Context context) {
        // Tạo dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_logout_confirmation, null);
        builder.setView(dialogView);

        // Tìm các thành phần trong dialog
        Button cancelButton = dialogView.findViewById(R.id.button_cancel);
        Button confirmButton = dialogView.findViewById(R.id.button_confirm);
        AlertDialog dialog = builder.create();

        // Sự kiện cho nút Hủy
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        // Sự kiện cho nút Đăng xuất
        confirmButton.setOnClickListener(v -> {
            // Thoát ứng dụng
            dialog.dismiss();
            logout(context);
        });
        // Hiển thị dialog
        dialog.show();
    }

    public static void logout(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Return to login screen
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Clear the activity stack
        context.startActivity(intent);
    }
}
