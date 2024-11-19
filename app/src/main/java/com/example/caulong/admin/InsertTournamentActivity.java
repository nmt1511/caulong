package com.example.caulong.admin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.tournament;

public class InsertTournamentActivity extends AppCompatActivity {
    private EditText etName, etStartDate, etEndDate, etDescription;
    private Button btnSave;
    private DataDatSan helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_tournament);

        etName = findViewById(R.id.etName_add);
        etStartDate = findViewById(R.id.etStartDate_add);
        etEndDate = findViewById(R.id.etEndDate_add);
        etDescription = findViewById(R.id.etDescription_add);
        btnSave = findViewById(R.id.btnSave_add);

        helper = new DataDatSan(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String startDate = etStartDate.getText().toString();
                String endDate = etEndDate.getText().toString();
                String description = etDescription.getText().toString();
                long id = saveTournament();
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                if(id != -1){
                    tournament Tournament = new tournament(id+" ", name, startDate, endDate, description);
                    bundle.putSerializable("tournament",Tournament);
                    intent.putExtra("data",bundle);
                    setResult(Tournament_List.SAVE_TOURNAMENT, intent);
                    Toast.makeText(getApplication(), "Thêm giải đấu thành công!!!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
    private long saveTournament(){
        db = helper.getWritableDatabase();
        String name = etName.getText().toString();
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        String description = etDescription.getText().toString();
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        try{
            values.put("name", name);
            values.put("start_date", startDate);
            values.put("end_date", endDate);
            values.put("description", description);
            return (db.insert("Tournament",null,values));
        }catch (Exception e){
            Toast.makeText(getApplication(), "Lỗi "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return -1;
    }
}
