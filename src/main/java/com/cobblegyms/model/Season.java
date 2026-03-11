package com.cobblegyms.model;

public class Season {

    private final int id;
    private final String startDate;
    private final String endDate;
    private final boolean active;

    public Season(int id, String startDate, String endDate, boolean active) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

    public int getId() { return id; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public boolean isActive() { return active; }

    @Override
    public String toString() {
        return "Season{id=" + id + ", startDate='" + startDate + "', endDate='" + endDate + "', active=" + active + "}";
    }
}