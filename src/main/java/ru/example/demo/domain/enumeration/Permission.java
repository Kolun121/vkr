package ru.example.demo.domain.enumeration;

public enum Permission {
    USER("user"),
    SUBJECT_ROLE("subject_user"),
    MUNICIPALITY_ROLE("municipality_user"),
    MCHS_ROLE("mchs_user"),
    ADMIN("admin");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}