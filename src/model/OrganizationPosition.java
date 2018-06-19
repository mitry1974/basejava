package model;

import java.time.YearMonth;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class OrganizationPosition {

    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String title;
    private final String description;

    public OrganizationPosition(YearMonth startDate, YearMonth endDate, String title, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationPosition that = (OrganizationPosition) o;

        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!title.equals(that.title)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(startDate);
        sb.append(" - ");
        sb.append(endDate);
        sb.append('\t');
        sb.append(title);
        if(description != null) {
            sb.append(description);
        }
        return sb.toString();
    }
}