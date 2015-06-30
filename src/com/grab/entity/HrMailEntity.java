package com.grab.entity;

import com.grab.Utils;

/**
 * Created by baiqunwei on 15/6/29.
 */
public class HrMailEntity {
    private String name;
    private String sentDate;
    private String source;
    private String title;
    private String type;
    private String fileName;

    public HrMailEntity(String fileName) {
        this.fileName = fileName;
        Utils.parseFileName(this, this.fileName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return "HrMailEntity{" +
                "name='" + name + '\'' +
                ", sentDate='" + sentDate + '\'' +
                ", source='" + source + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
