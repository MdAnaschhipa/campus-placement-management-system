package com.campusplacement.enums;

public enum ApplicationStatus {

    APPLIED("Applied"),
    IN_PROGRESS("In Progress"),
    SELECTED("Selected"),
    REJECTED("Rejected"),
    WITHDRAWN("Withdrawn");

    private final String displayName;

    ApplicationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}