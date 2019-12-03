import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Location {
	private String description;
	private HashMap roomExits;		//Like array, but stores "north" or "south" of a room as an exit.
	ArrayList<Item> items = new ArrayList<Item>();
	
	
	public Location(String description) {
		this.description = description;
		roomExits = new HashMap();
	}
	
	public void setExit(String direction, Location adjacent) {
		roomExits.put(direction, adjacent);
	}
	
	public String getDescription() {
		return "You are " + description + getExits();
	}
	
	public String getExits() {
		String returnOnExit = "Exits: ";
		Set keys = roomExits.keySet();
		//returns all possible exits from that room
		for (Iterator iterator = keys.iterator(); iterator.hasNext(); ) {
			returnOnExit = returnOnExit + iterator.next();
		}
		//returns all items in the room.
		returnOnExit = "\n\nItems in the room: \n";
		returnOnExit = returnOnExit + getLocationItems() + " ";
		
		return returnOnExit;
	}
	
	public Location getExit(String direction) {
        return (Location)roomExits.get(direction);
    }
	
	public Item getItem(int index) {
		return getItem(index);
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
	
	public void removeItem(String itemName) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getDescription().equals(itemName)) {
				items.remove(i);
			}
		}
	}
	
	public String getLocationItems() {
		String currentItems = "";
		for (int i = 0; i < items.size(); i++) {
			currentItems = currentItems + items.get(i).getDescription() + " ";
		}
		return currentItems;
	}
}
