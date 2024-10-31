package com.example.caulong.menuleft;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.R;

public class support extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
    }

    // Phương thức mở ứng dụng gọi điện với số điện thoại
    public void openPhoneNumber(View view) {
        String phoneNumber = "tel:0328450769"; // Thay bằng số điện thoại thực tế của bạn
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
        startActivity(intent);
    }
    // Mở Facebook
    public void openFacebook(View view) {
        openSocialMedia("https://www.facebook.com/profile.php?id=100074366473315", "com.facebook.katana");
    }

    // Mở Zalo
    public void openZalo(View view) {
        openSocialMedia("https://zalo.me/g/saeygb932", "com.zing.zalo");
    }

    // Mở Instagram
    public void openInstagram(View view) {
        openSocialMedia("https://www.instagram.com/sontungmtp?igsh=dGJwbnYxNjFzajNo", "com.instagram.android");
    }

    // Mở TikTok
    public void openTikTok(View view) {
        openSocialMedia("https://www.tiktok.com/@imjack1997?_t=8qwcyVxcnuW&_r=1", "com.zhiliaoapp.musically");
    }

    /**
     * Hàm chung để mở ứng dụng truyền thông xã hội hoặc mở trang web nếu ứng dụng không được cài đặt
     */
    private void openSocialMedia(String url, String packageName) {
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null) {
                // Mở ứng dụng nếu có
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } else {
                // Nếu không có ứng dụng, mở bằng trình duyệt
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(webIntent);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Không thể mở liên kết", Toast.LENGTH_SHORT).show();
        }
    }
}
