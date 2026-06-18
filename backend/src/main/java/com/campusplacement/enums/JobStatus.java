package com.campusplacement.enums;

public enum JobStatus {

    DRAFT("Draft"),
    OPEN("Open"),
    CLOSED("Closed"),
    EXPIRED("Expired");

    private final String displayName;

    JobStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}