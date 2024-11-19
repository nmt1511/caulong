package com.example.caulong.entities;

import java.io.Serializable;

public class tournament implements Serializable {
    public tournament(String tournamentId, String name, String startDate, String endDate, String description) {
        this.description = description;
        this.endDate = endDate;
        this.name = name;
        this.startDate = startDate;
        this.tournamentId = tournamentId;
    }

    private String tournamentId;
    private String name;
    private String startDate;
    private String endDate;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }
}
