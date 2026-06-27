package com.campusplacement.enums;

public enum AuditAction {

    LOGIN("Login"),
    APPROVE_RECRUITER("Approve Recruiter"),
    REJECT_RECRUITER("Reject Recruiter"),
    ACTIVATE_RECRUITER("Activate Recruiter"),
    DEACTIVATE_RECRUITER("Deactivate Recruiter"),
    ACTIVATE_STUDENT("Activate Student"),
    DEACTIVATE_STUDENT("Deactivate Student"),
    CLOSE_JOB("Close Job"),
    DELETE_JOB("Delete Job"),
    CREATE_ANNOUNCEMENT("Create Announcement"),
    UPDATE_ANNOUNCEMENT("Update Announcement"),
    DELETE_ANNOUNCEMENT("Delete Announcement");

    private final String displayName;

    AuditAction(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}