import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

public class Room {
	private String description;
	private HashMap roomExits;		//Like array, but stores "north" or "south" of a room as an exit.
	
	public Room(String description) {
		this.description = description;
		roomExits = new HashMap();
	}
	
	public void setExit(String direction, Room adjacent) {
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
	
	
}
