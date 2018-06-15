package model;

public class Contact {
    private String uuid;
    private ContactType type;
    private String data;

    public Contact(String uuid, ContactType type, String data) {
        this.type = type;
        this.data = data;
        this.uuid = uuid;
    }

    public String printContactType() {
        return type.toString();
    }

    public String printContactData() {
        return data;
    }

    public String getUuid() {
        return uuid;
    }
}

