package com.example.fewwind.chaozhuofirst.bean;

import java.io.Serializable;

/**
 * Created by fewwind on 17-3-1.
 */

public class FileObject implements Serializable {
    public  String title;
    int version;


    public FileObject(String title, int version) {
        this.title = title;
        this.version = version;
    }


    @Override public String toString() {
        return "FileObject{" +
            "title='" + title + '\'' +
            ", version=" + version +
            '}';
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public int getVersion() {
        return version;
    }


    public void setVersion(int version) {
        this.version = version;
    }
}
