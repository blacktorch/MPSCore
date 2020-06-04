package com.chidiebere.data;

import org.json.JSONObject;

public class Subject {
    private String title;
    private JSONObject data;

    public Subject(String title){
        this.title = title;
    }

    @Override
    public String toString(){
        return title;
    }

    public String getTitle() {
        return title;
    }

    public JSONObject getData() {
        if (data == null){
            data = new JSONObject("{}");
        }
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
