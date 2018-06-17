package model;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Organization {
    public Organization(Node rootNode) {
        loadXml(rootNode);
    }

    private final ArrayList<Position> positions = new ArrayList<>();
    protected String title;

    public void loadXml(Node rootNode) {
        NodeList list = rootNode.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeName().equals("title")) {
                title = node.getTextContent();
            }

            if (node.getNodeName().equals("position")) {
                positions.add(new Position(node));
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title + '\n');
        for (Position p : positions) {
            sb.append(p.toString());
        }

        return sb.toString();
    }

    class Position {
        public Position(Node rootNode) {
            if (rootNode != null)
                loadXml(rootNode);
        }

        private String title;
        private YearMonth startDate;
        private YearMonth finishDate;
        private String data;

        private void loadXml(Node rootNode) {
            NodeList elementsList = rootNode.getChildNodes();
            for (int j = 0; j < elementsList.getLength(); j++) {
                Node elementNode = elementsList.item(j);
                if (elementNode.getNodeName().equals("datestart")) {
                    startDate = loadXmlDate(elementNode);
                }

                if (elementNode.getNodeName().equals("datefinish")) {
                    finishDate = loadXmlDate(elementNode);
                }

                if (elementNode.getNodeName().equals("title")) {
                    title = elementNode.getTextContent();
                }

                if (elementNode.getNodeName().equals("data")) {
                    data = elementNode.getTextContent();
                }

            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(printDate(startDate) + " - " + printDate(finishDate) + '\n');
            if (title != null)
                sb.append(title + '\n');
            if (data != null)
                sb.append(data + '\n');
            return sb.toString();
        }

        private YearMonth loadXmlDate(Node node) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
            String strdate = node.getTextContent();
            if (strdate.equals("now")) {
                return YearMonth.now();
            }
            return YearMonth.parse(strdate, formatter);
        }

        private String printDate(YearMonth ym) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyy");

            return ym.format(formatter);
        }
    }
}


