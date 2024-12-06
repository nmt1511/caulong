package com.example.caulong.menubottom;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.caulong.R;
import com.example.caulong.admin.TournamentAdapter;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.tournament;

import java.util.ArrayList;

public class GiaiDauFragment extends Fragment {
    SQLiteDatabase db;
    DataDatSan helper;
    private RecyclerView recyclerView;
    private TournamentAdapter adapter;
    private ArrayList<tournament> tournamentList;

    public GiaiDauFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_giai_dau, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewTournaments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize DataDatSan helper class
        helper = new DataDatSan(getContext());

        // Get all tournaments from database
        tournamentList = getAllTournaments();

        // Set adapter to RecyclerView
        adapter = new TournamentAdapter(tournamentList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Fetch tournaments from the database
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
