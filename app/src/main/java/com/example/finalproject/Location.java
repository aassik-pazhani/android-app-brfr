package com.example.finalproject;
//import java.util.ArrayList;
//import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

public class Location {
    private String description;
    private HashMap<String, Location> roomExits;		//Like array, but stores "north" or "south" of a room as an exit.

    private List<Item> items = new ArrayList<>();

    Location(String description) {
        this.description = description;
        roomExits = new HashMap<>();
    }

    public void setExit(String direction, Location adjacent) {
        roomExits.put(direction, adjacent);
    }

    public String getDescription() {
        return "You are " + description + getExits();
    }

    public String getExits() {
        String exits = "Exits: ";
        Set<String> keys = roomExits.keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
            exits = exits + iterator.next();
        }
        return exits;
    }

    public Location getExit(String direction) {
        return roomExits.get(direction);
    }
    public Item getItem(int index) {
        return items.get(index);
    }
    public Item getItem(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getDescription().equals(itemName)) {
                return items.get(i);
            }
        }
        return null;
    }

    public void setItem(Item newItem) {
        items.add(newItem);
    }
}
