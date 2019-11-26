package com.example.finalproject;

public class Action {

    private String action;
    private String object;

    public Action(String action, String object) {
        this.action = action;
        this.object = object;
    }

    public String getAction() {
        return action;
    }

    public String getObject() {
        return object;
    }

    public boolean hasAction() {
        return (action == null);
    }

    public boolean hasObject() {
        return (object != null);
    }

}


