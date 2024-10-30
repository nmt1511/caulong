package com.example.caulong.menuleft;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.caulong.R;

public class ShareFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        view.findViewById(R.id.icon_twitter).setOnClickListener(v -> openSocialMedia("https://twitter.com"));
        view.findViewById(R.id.icon_facebook).setOnClickListener(v -> openSocialMedia("https://facebook.com"));
        view.findViewById(R.id.icon_instagram).setOnClickListener(v -> openSocialMedia("https://instagram.com"));
        view.findViewById(R.id.icon_zalo).setOnClickListener(v -> openSocialMedia("https://zalo.me"));
        view.findViewById(R.id.icon_tiktok).setOnClickListener(v -> openSocialMedia("https://tiktok.com"));

        return view;
    }

    private void openSocialMedia(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
