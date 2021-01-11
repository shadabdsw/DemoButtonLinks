package com.example.demobuttonlinks;

public class ButtonModel {
    private final String label;
    private final String link;
    private String key;

    public ButtonModel() {
        this.label = "";
        this.link = "";
    }

    public ButtonModel(String label, String link) {
        this.label = label;
        this.link = link;
    }

    public String getLabel() {
        return label;
    }

    public String getLink() {
        return link;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
