//Assigns first word inputed in console as the action word.
//Assigns the second word following the first as the object word.
//if the user does not input a second word, the object is null.

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
