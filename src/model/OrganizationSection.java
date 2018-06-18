package model;

import java.util.ArrayList;

public class OrganizationSection implements Section {
    private final ArrayList<Organization> organizations = new ArrayList<>();

    public OrganizationSection() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization o : organizations) {
            sb.append(o.toString());
        }

        return sb.toString();
    }

    @Override
    public void clearData() {
        organizations.clear();
    }

    public void addOrganization(Organization o) {
        organizations.add(o);
    }
}
