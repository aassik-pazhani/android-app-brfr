package com.example.finalproject;

public class ActionWords {

    private static final String actions[] = {
            "go", "quit", "help"
    };

    public ActionWords() {

    }

    public boolean checkAction(String action) {

        for(int i = 0; i < actions.length; i++) {
            if (actions[i].equals(action)) {
                return true;
            }
        }
        return false;
    }

    public void showActions() {

        for (int i = 0; i < actions.length; i++) {
            System.out.println(actions[i] + "   ");
        }
        System.out.println();
    }
}