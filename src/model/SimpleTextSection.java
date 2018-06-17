package model;

import org.w3c.dom.Node;

public class SimpleTextSection implements Section{
    private String title;
    private String data;

    protected SimpleTextSection(String title) {
        this.title = title;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void clearData() {
        data = "";
    }

    @Override
    public void loadXml(Node rootNode) {
        data = rootNode.getTextContent();
    }

    @Override
    public String toString() {
        return title + '\n' + data + '\n';
    }
}
