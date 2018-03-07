package com.sterbon.tutex;

/**
 * Created by Utsav on 1/7/2018.
 */

public class Files {
    private String name;
    private String link;
    private String content;
    private int down_count;
    private String file_id;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public int getDown_count() {
        return down_count;
    }

    public void setDown_count(int down_count) {
        this.down_count = down_count;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
