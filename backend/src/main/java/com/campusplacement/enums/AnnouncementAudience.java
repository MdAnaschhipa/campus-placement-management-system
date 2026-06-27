package com.campusplacement.enums;

public enum AnnouncementAudience {

    ALL("All Users"),
    STUDENTS("Students"),
    RECRUITERS("Recruiters");

    private final String displayName;

    AnnouncementAudience(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}