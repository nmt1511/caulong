package com.example.caulong.menubottom;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import static android.content.Context.MODE_PRIVATE;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.caulong.R;
import com.example.caulong.booking.Booking_yard;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.menuleft.support;
import com.example.caulong.user.InfoActivity;
import com.example.caulong.user.LogoutActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class AccountFragment extends Fragment {
    private ImageView imgAvatar;
    private Button btn_ChangeAvatar;
    TextView  txtPersonalInfo,txtInfo,txtShare,logout;
    int CustomerId;
    SQLiteDatabase db;
    DataDatSan helper;
    private ActivityResultLauncher<Intent> cameraLauncher;//sử dụng Activity Result API
    private ActivityResultLauncher<Intent> galleryLauncher;

    void init(View v){
        imgAvatar = v.findViewById(R.id.imgProfile);
        btn_ChangeAvatar = v.findViewById(R.id.btn_Doi_Anh);
        txtPersonalInfo = v.findViewById(R.id.txtPersonalInfo);
        txtInfo = v.findViewById(R.id.txtInfo);
        txtShare = v.findViewById(R.id.txtShare);
        logout = v.findViewById(R.id.logout);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account, container, false);
        helper = new DataDatSan(getContext());
        init(view);
        txtPersonalInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InfoActivity.class);
            startActivity(intent);
        });
        txtInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), support.class);
            startActivity(intent);
        });

        btn_ChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Đổi ảnh đại diện")
                        .setItems(new CharSequence[]{"Chụp ảnh", "Chọn từ thư viện"}, (dialog, which) -> {
                            if (which == 0) {
                                // Tùy chọn 1: Chụp ảnh
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                cameraLauncher.launch(intent);
                            } else if (which == 1) {
                                // Tùy chọn 2: Chọn từ thư viện
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                galleryLauncher.launch(intent);
                            }
                        })
                        .show();
            }
        });
        helper = new DataDatSan(getContext());
        SharedPreferences preferences = requireContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        CustomerId = preferences.getInt("customerId", -1);
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Bitmap photo = (Bitmap) data.getExtras().get("data");
                            //lưu ảnh vào bộ nhớ trong
                            String imagePath = saveImageToInternalStorage(photo);
                            if (imagePath != null) {
                                // Lưu đường dẫn vào CSDL
                                saveAvatarPathToDatabase(imagePath);
                                // Hiển thị ảnh lên ImageView
                                imgAvatar.setImageBitmap(photo);
                            }
                        }
                    }
                }
        );
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            try {
                                // Lấy đường dẫn ảnh từ thư viện
                                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                                // Lưu ảnh vào bộ nhớ trong
                                String imagePath = saveImageToInternalStorage(selectedImage);
                                if (imagePath != null) {
                                    // Lưu đường dẫn vào CSDL
                                    saveAvatarPathToDatabase(imagePath);
                                    // Hiển thị ảnh lên ImageView
                                    imgAvatar.setImageBitmap(selectedImage);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        helper.loadAvatarFromDatabase(imgAvatar, CustomerId);
        return view;

    }

    private String saveImageToInternalStorage(Bitmap bitmap) {
        File directory = getContext().getFilesDir(); // Thư mục lưu trữ của ứng dụng
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String avatar = day + "-" + month + "-" + year+"ID"+CustomerId;
        File imageFile = new File(directory, avatar + ".jpg");

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            return imageFile.getAbsolutePath(); // Trả về đường dẫn của ảnh
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveAvatarPathToDatabase(String path) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("avatar", path);
        db.update("Customer", values, "customer_id = ?", new String[]{String.valueOf(CustomerId)});
        db.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}