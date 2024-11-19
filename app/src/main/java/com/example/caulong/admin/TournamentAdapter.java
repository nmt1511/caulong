package com.example.caulong.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.entities.tournament;

import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.TournamentViewHolder> {
    private List<tournament> tournamentList;

    public TournamentAdapter(List<tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @NonNull
    @Override
    public TournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tournament, parent, false);
        return new TournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentViewHolder holder, int position) {
        tournament tournament = tournamentList.get(position);
        holder.name.setText(tournament.getName());
        holder.StartDate.setText(tournament.getStartDate());
        holder.EndDate.setText(tournament.getEndDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(v.getContext(), EditTournamentActivity.class);
                //intent.putExtra("tournament_id", tournament.getTournamentId());
                bundle.putSerializable("tournament",tournament);
                intent.putExtra("data", bundle);
                ((Activity) v.getContext()).startActivityForResult(intent, Tournament_List.EDIT_TOURNAMENT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public static class TournamentViewHolder extends RecyclerView.ViewHolder {
        TextView name, StartDate, EndDate;

        public TournamentViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tournamentName);
            StartDate = itemView.findViewById(R.id.tournamentStartDates);
            EndDate = itemView.findViewById(R.id.tournamentEndDates);
        }
    }
}
