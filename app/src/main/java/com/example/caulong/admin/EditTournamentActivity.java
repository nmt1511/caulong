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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.tournament;

public class EditTournamentActivity extends AppCompatActivity {
    private EditText etName, etStartDate, etEndDate, etDescription;
    private Button btnSave;
    private DataDatSan helper;
    SQLiteDatabase db;
    private String tournamentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tournament);

        etName = findViewById(R.id.etName);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);

        helper = new DataDatSan(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        if(bundle != null){
            tournament Tournament = (tournament) bundle.getSerializable("tournament");
            if(Tournament != null){
                tournamentId = Tournament.getTournamentId();
                tournament tournament = getTournamentById(tournamentId);
                if (tournament != null) {
                    etName.setText(tournament.getName());
                    etStartDate.setText(tournament.getStartDate());
                    etEndDate.setText(tournament.getEndDate());
                    etDescription.setText(tournament.getDescription());
                }
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String startDate = etStartDate.getText().toString();
                String endDate = etEndDate.getText().toString();
                String description = etDescription.getText().toString();
                if(updateTournament()){
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    tournament Tournament = new tournament(tournamentId, name, startDate, endDate, description);
                    bundle.putSerializable("tournament",Tournament);
                    intent.putExtra("data", bundle);
                    setResult(Tournament_List.SAVE_EDIT_TOURNAMENT, intent);
                    Toast.makeText(getApplication(), "Cập nhật giải đấu thành công!!!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
    public tournament getTournamentById(String id) {
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Tournament WHERE tournament_id=?", new String[]{id});

        if (cursor != null && cursor.moveToFirst()) {
            tournament tournament = new tournament(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            cursor.close();
            return tournament;
        }
        return null;
    }

    public boolean updateTournament() {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String name = etName.getText().toString();
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        String description = etDescription.getText().toString();
        try{
            values.put("name", name);
            values.put("start_date", startDate);
            values.put("end_date", endDate);
            values.put("description", description);
            if(db.update("Tournament", values, "tournament_id = ?",
                    new String[]{tournamentId}) != -1)
                return true;
        }catch (Exception e){
            Toast.makeText(getApplication(), "Lỗi "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
