package be.kdg.film_project.domain;

public enum UserRole {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String code;

    UserRole(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
