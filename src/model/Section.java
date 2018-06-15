package model;

public interface Section {
    public abstract String      toString();
    public abstract void        setData(String[] data);
    public abstract String[]    getData();
    public abstract String      getTitle();
    public abstract void        clearData();
}
