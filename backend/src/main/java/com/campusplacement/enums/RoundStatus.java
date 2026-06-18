package com.campusplacement.enums;

public enum RoundStatus {

    PENDING("Pending"),
    CLEARED("Cleared"),
    REJECTED("Rejected"),
    NOT_APPLICABLE("Not Applicable");

    private final String displayName;

    RoundStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}