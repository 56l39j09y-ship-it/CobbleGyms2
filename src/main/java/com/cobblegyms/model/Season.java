package com.cobblegyms.model;

public class Season {
    private final int id;
    private final String startDate;
    private final String endDate;

    public Season(int id, String startDate, String endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() { return id; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public boolean isActive() { return endDate == null; }

    @Override
    public String toString() {
        return "Season{id=" + id + ", start=" + startDate + ", end=" + endDate + "}";
    }
}