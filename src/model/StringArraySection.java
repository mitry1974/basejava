package model;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class StringArraySection implements Section {
    private List<String> array = new ArrayList<>();
    private String title;

    protected StringArraySection(String title) {
        this.title = title;
    }

    @Override
    public void loadXml(Node rootNode) {
        NodeList list = rootNode.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeName().equals("string")) {
                array.add(node.getTextContent());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title + '\n');
        for (String s : array) {
            sb.append(s);
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public void clearData() {
        array.clear();
    }
}
