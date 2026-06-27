package com.campusplacement.enums;

public enum AuditEntityType {

    AUTH("Authentication"),
    USER("User"),
    STUDENT("Student"),
    RECRUITER("Recruiter"),
    JOB("Job"),
    ANNOUNCEMENT("Announcement");

    private final String displayName;

    AuditEntityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}