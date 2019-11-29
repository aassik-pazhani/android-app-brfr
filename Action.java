//Assigns first word inputed in console as the action word.
//Assigns the second word following the first as the object word.
//if the user does not input a second word, the object is null.

public class Action {
	
	private String action;
	private String object;
	
	public Action(String actionWord, String objectWord) {
		this.action = actionWord;
		this.object = objectWord;
	}
	
	public String getAction() {
		return action;
	}
	
	public String getObject() {
		return object;
	}
	
	//checks if an action word exists ex: go, get, drop, etc.
	public boolean hasAction() {
		return (action == null);
	}
	
	public boolean hasObject() {
		return (object != null);
	}
}
