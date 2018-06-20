package model;

import util.DateUtil;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static util.DateUtil.NOW;

public class Organization implements Serializable {
    private final Link homePage;

    private final List<Position> positions;

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Organization(Link homePage, List<Position> positions) {
        Objects.requireNonNull(positions, "title must not be null");
        this.homePage = homePage;
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
        for (Position p : positions) {
            sb.append(p.toString());
            sb.append('\n');
        }
        return sb.toString();
    }

    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;


        private final YearMonth startDate;
        private final YearMonth endDate;
        private final String title;
        private final String description;

        public Position(int startYear, int startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), NOW, title, description);
        }

        public Position(int startYear, int startMonth, int endYear, int endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

        public Position(YearMonth startDate, YearMonth endDate, String title, String description) {
            Objects.requireNonNull(startDate);
            Objects.requireNonNull(endDate);
            Objects.requireNonNull(title);
            Objects.requireNonNull(description);
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position that = (Position) o;

            if (!startDate.equals(that.startDate)) return false;
            if (!endDate.equals(that.endDate)) return false;
            if (!title.equals(that.title)) return false;
            return description != null ? description.equals(that.description) : that.description == null;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(startDate);
            sb.append(" - ");
            sb.append(endDate);
            sb.append('\t');
            sb.append(title);
            if (description != null) {
                sb.append(description);
            }
            return sb.toString();
        }
    }
}


