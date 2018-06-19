package model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    private final List<Organization> organizations;

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must be not null");
        this.organizations = organizations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization o : organizations) {
            sb.append(o.toString());
        }

        return sb.toString();
    }

    public void addOrganization(Organization o) {
        Objects.requireNonNull(o);
        organizations.add(o);
    }
}
