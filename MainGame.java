import java.util.ArrayList;

//Main class which initializes all other classes.

public class MainGame {
	
	private Location currentLocation;
	private ActionWords actions;
	private Action action;
	Location union, mainQuad, ugl, ike, lar, par, chemAnnex, church, altgeld, engHall, loomis, grainger, siebel, northQuad, ece;
	
	ArrayList<Item> inventory = new ArrayList<Item>();
	
	public static void main(String[] args) {
		 MainGame newGame = new MainGame();
		 newGame.play();
	}
	
	public MainGame() {
		createLocations();
	}
	
	private void createLocations() {
		
		//creating the rooms
		union = new Location("inside the Illini Union. Students bustle about the tattered facility.");
		mainQuad = new Location ("looking across the Main Quad. You see Foellinger, and notheing else notable.");
		ugl = new Location("inhaling fresh espresso brewed in the Undergraduate Library. You take a look downstairs and see the miserable lives of undergraduates in their depressive state. \nYou shudder at the thought of first semester of freshman year with Geoff.");
		ike = new Location("standing in front of the Ikenberry Commons, staring into 57. You once loved this place. At the worst of times, you knew 57's spicy chicken sandwiches got you through an afternoon.");
		lar = new Location("at LAR. You don't know much about this place, other than it is called LAR.");
		par = new Location("at PAR, for some odd reason. Traveled all the way down here for... what? At least you lost 1500 calories trying to get here.");
		chemAnnex = new Location("in the Chem Annex. You head into the supply room. Flasks and chemicals line the shelves, free for taking. Only if you took OChem... then maybe you could make your own Codine rather than buy it through Trippy Tom...");
		church = new Location("standing at the back row of Methodist Church. You stare at the stained windows and the large podium where the pastor stands.");
		altgeld = new Location("standing in the narrow halls of altgeld mathematics hall. Terrible flashbacks of falling asleep in lecture suddenly clouds your mind.");
		engHall = new Location("staring into the lounge of the engineering hall. You stare at the students sitting in the loung awaiting advising; their poor souls staring blankly into their degree audits...");
		loomis = new Location("standing in front of a large plasma ball in Loomis Lab.");
		grainger = new Location("finally north of Green St., at Grainger Engineering Library. You stare across Green St., looking at the great disparity between north of Green and south of Green. You feel content all of a sudden...");
		siebel = new Location("smelling pizza, the signature scent of Siebel. Posters of Geoff line the walls of the building while students stare blankly at their screen. You peek over the shoulder of one student. The screen reads: 'MP78: The P versus NP Problem.'");
		northQuad = new Location("in the cold, desolate North Quad. Only engineering students were on the North Quad, but then you remembered engineering students live at siebel and the ece building...");
		ece = new Location("suffocating to the smell of body odor in the ECE Building. You look around the glass windows and see hundreds of robotic parts scattered around the laboratory floor.");

		
		union.setExit("north", engHall);
		union.setExit("south", mainQuad);
		union.setExit("east", church);
		union.setExit("west", altgeld);
		
		mainQuad.setExit("north", union);
		mainQuad.setExit("south", ugl);
		mainQuad.setExit("east", chemAnnex);
		
		ugl.setExit("north", mainQuad);
		ugl.setExit("east", lar);
		ugl.setExit("west", ike);
		
		ike.setExit("east", ugl);
		
		lar.setExit("south", par);
		lar.setExit("west", ugl);
		
		par.setExit("north", lar);
		
		chemAnnex.setExit("north", church);
		chemAnnex.setExit("west", mainQuad);
		
		church.setExit("north", loomis);
		church.setExit("south", chemAnnex);
		church.setExit("west", union);
		
		altgeld.setExit("east", union);
		
		engHall.setExit("north", grainger);
		engHall.setExit("south", union);
		engHall.setExit("east", loomis);
		
		loomis.setExit("north", siebel);
		loomis.setExit("south", church);
		loomis.setExit("west", engHall);
		
		grainger.setExit("north", northQuad);
		grainger.setExit("south", engHall);		
		
		siebel.setExit("south", loomis);
		siebel.setExit("west", northQuad);
		
		northQuad.setExit("south", grainger);
		northQuad.setExit("east", siebel);
		northQuad.setExit("west", ece);
		
		ece.setExit("east", northQuad);
		
		//we start the room outside
		currentLocation = union;
		
		//initial item in inventory, can add items to inventory by picking up items from rooms
		inventory.add(new Item("I-Card"));
		
		//setting up items in rooms
		union.setItem(new Item("piano"));
		
		chemAnnex.setItem(new Item("flask"));
		chemAnnex.setItem(new Item("strong acid"));
		
		grainger.setItem(new Item("access code"));
		
		
	}
	
	public void play() {
		//prints instructions for the game
		System.out.println("You recently forgot to do MP71.");
		System.out.println("Your grade has dropped to an F.");
		System.out.println("Task: Find the access code to Siebel's data center and change your grade to an A.");
		System.out.println();
		System.out.println(currentLocation.getDescription());
		//System.out.println("Your action words are: ");
		//actions.showActions();
		
		boolean finished = false;
		while (!finished) {
			Action action = null;
			finished = processAction(action);
		}
		System.out.println("ty for playing. goodbye.");
	}
	
	private boolean processAction(Action action) {
        boolean wantToQuit = false;

//        if(action.hasAction()) {
//            System.out.println("I don't know what you mean...");
//            return false;
//        }
        
        if(action.hasAction()) {
        	System.out.println("I don't understand.");
        	return false;
        }
        
        String actionWord = action.getAction();
        if (actionWord.equals("help")) {
        	System.out.println();
            System.out.println("Your action words are: ");
        } else if (actionWord.equals("go")) {
        	//going to siebel now is the winning condition
        	wantToQuit = goTo(action);
        } else if (actionWord.equals("quit")) {
            wantToQuit = quit(action);
        } else if (actionWord.equals("inventory")) {
        	printInventory();
        } else if (actionWord.equals("get")) {
        	getItem(action);
        }
        return wantToQuit;
	}
	
	private void printInventory() {
		String currentInv  = "";
		for (int i = 0; i < inventory.size(); i++) {
			currentInv = currentInv + inventory.get(i).getDescription() + " ";
		}
		System.out.println("Inventory: " + currentInv);
	}

	public boolean goTo(Action action) {
		String direction = action.getObject();
		//Leaving current location to go to another location
		Location nextLocation = currentLocation.getExit(direction);
		if (nextLocation == null) {
			System.out.println("Nothing to the " + direction + " of " + currentLocation + "!");
		} else {
			currentLocation = nextLocation;
			System.out.println(currentLocation.getDescription());
			if (currentLocation == siebel && inventory.contains("access code")) {
				System.out.println("You win!");
				return true;
			}
		}
		return false;
	}
	
	public void getItem(Action action) {
		//the item will be the second word (action + object);
		String item = action.getObject();
		//Leaving current location to go to another location
		Item newItem = currentLocation.getItem(item);
		//if location method getItem returns null, it means item does not exist
		if (newItem == null) {
			System.out.println("That item does not exist!");
		} else { //else, remove the item from the room and add it to your inventory.
			currentLocation.removeItem(item);
			inventory.add(newItem);
			System.out.println("picked up: " + item);
		}
	}
	
	public boolean quit(Action action) {
		return true;
	}
}
