package model;

public enum ContactType {
    EMAIL("Почта"),
    PHONE("Телефон"),
    SKYPE("Skype"),
    LINK("Link");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
