package model;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class OrganizationSection implements Section {
    private String title;
    private final ArrayList<Organization> organizations = new ArrayList<>();

    public OrganizationSection(String title) {
        this.title = title;
    }

    @Override
    public void loadXml(Node rootNode) {
        NodeList list = rootNode.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeName().equals("title")) {
                title = node.getTextContent();
            }

            if (node.getNodeName().equals("organization")) {
                organizations.add(new Organization(node));
            }
        }
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

    }
}
