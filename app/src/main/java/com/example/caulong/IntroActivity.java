package com.example.caulong;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.user.LoginActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Khởi tạo VideoView để phát video
        VideoView videoView = findViewById(R.id.videoView);

        // Lấy URI của video từ thư mục raw
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro); // Đổi intro_video thành tên file video của bạn
        videoView.setVideoURI(videoUri);

        // Khi video phát xong, chuyển sang LoginActivity
        videoView.setOnCompletionListener(mp -> {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            finish(); // Đóng IntroActivity để người dùng không quay lại được
        });

        videoView.setOnCompletionListener(mp -> {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });

        // Bắt đầu phát video
        videoView.start();
    }
}
