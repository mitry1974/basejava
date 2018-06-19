package model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;

    private final List<OrganizationPosition> positions;

    public Organization(String name, List<OrganizationPosition> positions, String url) {
        Objects.requireNonNull(positions, "title must not be null");
        this.homePage = new Link(name, url);
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;

        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return homePage.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(homePage.toString());
        sb.append('\n');
        for (OrganizationPosition p : positions) {
            sb.append(p.toString());
            sb.append('\n');
        }
        return sb.toString();
    }
}


