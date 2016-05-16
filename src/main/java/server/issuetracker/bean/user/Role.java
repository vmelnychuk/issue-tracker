package server.issuetracker.bean.user;

public enum Role {
    GUEST("guest"), USER("user"), ADMIN("admin");

    private String title;

    Role(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
