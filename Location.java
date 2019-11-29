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
		String exits = "Exits: ";
		Set keys = roomExits.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext(); ) {
			exits = exits + iterator.next();
		}
		return exits;
	}
	
	public Location getExit(String direction) {
        return (Location)roomExits.get(direction);
    }
	
	public Item getItem(int index) {
		return getItem(index);
	}
	
	public void setItem(Item newItem) {
		items.add(newItem);
	}
	
	public String getRoomItems() {
		String output = " ";
		
	}
}
