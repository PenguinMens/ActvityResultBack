package com.example.actvityresultback;

public class MyDataManager
{
    private static MyDataManager instance;

    private String title;

    private String body;

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    private int meta;

    public static MyDataManager getInstance(){
        if(instance==null){
            instance = new MyDataManager();
        }
        return instance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
