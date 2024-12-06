package com.example.caulong.admin;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.tournament;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Tournament_List extends Activity {
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;
    private ArrayList<tournament> tournamentList;
    private TournamentAdapter adapter;
    SQLiteDatabase db;
    DataDatSan helper;
    public static final int OPEN_TOURNAMENT = 113;
    public static final int EDIT_TOURNAMENT = 114;
    public static final int SAVE_TOURNAMENT = 115;
    public static final int SAVE_EDIT_TOURNAMENT = 116;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_list);
        recyclerView = findViewById(R.id.d_rvTournament);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        helper = new DataDatSan(this);
        tournamentList = getAllTournaments();
        adapter = new TournamentAdapter(tournamentList);
        recyclerView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tournament_List.this, InsertTournamentActivity.class);
                startActivityForResult(intent,Tournament_List.OPEN_TOURNAMENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case OPEN_TOURNAMENT:
                if(resultCode == SAVE_TOURNAMENT){
                    Bundle bundle = data.getBundleExtra("data");
                    tournament Tournament = (tournament) bundle.getSerializable("tournament");
                    tournamentList.add(Tournament);
                    adapter.notifyDataSetChanged();
                }
                break;
            case EDIT_TOURNAMENT:
                if(resultCode == SAVE_EDIT_TOURNAMENT){
                    Bundle bundle = data.getBundleExtra("data");
                    tournament Tournament = (tournament) bundle.getSerializable("tournament");
                    for (int i = 0; i < tournamentList.size(); i++) {
                        if (tournamentList.get(i).getTournamentId() == Tournament.getTournamentId()) {
                            tournamentList.set(i, Tournament);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public ArrayList<tournament> getAllTournaments() {
        ArrayList<tournament> tournamentList = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Tournament", null);

        if (cursor.moveToFirst()) {
            do {
                tournament tournament = new tournament(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                tournamentList.add(tournament);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tournamentList;
    }
}
