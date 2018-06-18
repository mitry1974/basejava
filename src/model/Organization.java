package model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Organization {
    private String title;

    public Organization(String title) {
        this.title = title;
    }

    private final ArrayList<Position> positions = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        sb.append('\n');
        for (Position p : positions) {
            sb.append(p.toString());
        }

        sb.append('\n');

        return sb.toString();
    }

    public void addPosition(String startDate, String finishDate, String title, String data) {
        positions.add(new Position(startDate, finishDate, title, data));
    }

    class Position {
        public Position(String startDate, String finishDate, String title, String data) {
            this.startDate = parceDate(startDate);
            this.finishDate = parceDate(finishDate);
            this.title = title;
            this.data = data;
        }

        private String title;
        private YearMonth startDate;
        private YearMonth finishDate;
        private String data;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(printDate(startDate));
            sb.append(" - ");
            sb.append(printDate(finishDate));
            sb.append('\t');
            if (title != null) {
                sb.append(title);
                sb.append('\n');
            }

            if (data != null) {
                sb.append(data);
            }

            return sb.toString();
        }

        private String printDate(YearMonth ym) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyy");

            return ym.format(formatter);
        }

        private YearMonth parceDate(String date) {
            if (date.equals("now"))
                return YearMonth.now();
            else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyy");
                return YearMonth.parse(date, formatter);
            }
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public YearMonth getStartDate() {
            return startDate;
        }

        public void setStartDate(YearMonth startDate) {
            this.startDate = startDate;
        }

        public YearMonth getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(YearMonth finishDate) {
            this.finishDate = finishDate;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}


