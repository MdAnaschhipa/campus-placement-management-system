package com.campusplacement.enums;

public enum InterviewMode {

    ONLINE("Online"),
    OFFLINE("Offline"),
    HYBRID("Hybrid");

    private final String displayName;

    InterviewMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}