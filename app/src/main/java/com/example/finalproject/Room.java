package com.example.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Room extends AppCompatActivity {

    public static MediaPlayer mediaPlayer;

    List<Item> inventory = new ArrayList<>();


    private Location currentLocation;
    List<Integer> visited = new ArrayList<>();
    Location union, mainQuad, ugl, ike, lar, par, chemAnnex, church, altgeld, engHall, loomis, grainger, siebel, northQuad, ece;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.room);

        mediaPlayer = MediaPlayer.create(Room.this, R.raw.win);


        //creating the rooms
        union = new Location("inside the Illini Union. Students bustle about the tattered facility.", 0);
        mainQuad = new Location ("looking across the Main Quad. You see Foellinger, and notheing else notable.", 5);
        ugl = new Location("inhaling fresh espresso brewed in the Undergraduate Library. You take a look downstairs and see the miserable lives of undergraduates in their depressive state. \nYou shudder at the thought of first semester of freshman year with Geoff.", 1);
        ike = new Location("standing in front of the Ikenberry Commons, staring into 57. You once loved this place. At the worst of times, you knew 57's spicy chicken sandwiches got you through an afternoon.", 2);
        lar = new Location("at LAR. You don't know much about this place, other than it is called LAR.", 3);
        par = new Location("at PAR, for some odd reason. Traveled all the way down here for... what? At least you lost 1500 calories trying to get here.", 4);
        chemAnnex = new Location("in the Chem Annex. You head into the supply room. Flasks and chemicals line the shelves, free for taking. Only if you took OChem... then maybe you could make your own Codine rather than buy it through Trippy Tom...", 6);
        church = new Location("standing at the back row of Methodist Church. You stare at the stained windows and the large podium where the pastor stands.", 7);
        altgeld = new Location("standing in the narrow halls of altgeld mathematics hall. Terrible flashbacks of falling asleep in lecture suddenly clouds your mind.", 8);
        engHall = new Location("staring into the lounge of the engineering hall. You stare at the students sitting in the loung awaiting advising; their poor souls staring blankly into their degree audits...", 9);
        loomis = new Location("standing in front of a large plasma ball in Loomis Lab.", 10);
        grainger = new Location("finally north of Green St., at Grainger Engineering Library. You stare across Green St., looking at the great disparity between north of Green and south of Green. You feel content all of a sudden...", 11);
        siebel = new Location("smelling pizza, the signature scent of Siebel. Posters of Geoff line the walls of the building while students stare blankly at their screen. You peek over the shoulder of one student. The screen reads: 'MP78: The P versus NP Problem.'", 12);
        northQuad = new Location("in the cold, desolate North Quad. Only engineering students were on the North Quad, but then you remembered engineering students live at siebel and the ece building...", 13);
        ece = new Location("suffocating to the smell of body odor in the ECE Building. You look around the glass windows and see hundreds of robotic parts scattered around the laboratory floor.", 14);


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
        currentLocation = union;
        //initial item in inventory, can add items to inventory by picking up items from rooms
        inventory.add(new Item("I-Card"));

        //setting up items in rooms
        engHall.setItem(new Item("siebel key"));

        ece.setItem(new Item("challen's usb drive"));

        grainger.setItem(new Item("access code"));
        gameplay();


    }

    private void gameplay() {
        boolean finished = false;
        while (!finished) {
            finished = processAction();
        }
    }
    private int count = 0;
    private int lifecount = 15;

    public boolean processAction() {
        TextView life = findViewById(R.id.life);
        Button north = findViewById(R.id.north);
        Button south = findViewById(R.id.south);
        Button east = findViewById(R.id.east);
        Button west = findViewById(R.id.west);
        Button quit = findViewById(R.id.quitbutton);
        AtomicBoolean toReturn = new AtomicBoolean(false);
        TextView question = findViewById(R.id.question);
        Random rand = new Random();
        TextView ansA = findViewById(R.id.answerA);
        TextView ansB = findViewById(R.id.answerB);
        TextView ansC = findViewById(R.id.answerC);
        TextView ansD = findViewById(R.id.answerD);
        RadioButton optA =findViewById(R.id.optionA);
        RadioButton optB =findViewById(R.id.optionB);
        RadioButton optC =findViewById(R.id.optionC);
        RadioButton optD =findViewById(R.id.optionD);
        final String[] correct_answer = new String[1];
        RadioGroup options = findViewById(R.id.options);
        TextView description = findViewById(R.id.description);
        question.setVisibility(View.VISIBLE);
        optA.setVisibility(View.VISIBLE);
        optB.setVisibility(View.VISIBLE);
        optC.setVisibility(View.VISIBLE);
        optD.setVisibility(View.VISIBLE);
        ansA.setVisibility(View.VISIBLE);
        ansB.setVisibility(View.VISIBLE);
        ansC.setVisibility(View.VISIBLE);
        ansD.setVisibility(View.VISIBLE);
        ansA.setTextColor(Color.WHITE);
        ansB.setTextColor(Color.WHITE);
        ansC.setTextColor(Color.WHITE);
        ansD.setTextColor(Color.WHITE);
        question.setTextColor(Color.WHITE);
        life.setTextColor(Color.WHITE);
        description.setTextColor(Color.WHITE);

        if (lifecount > 0) {
            String text = "Remaining life is " + lifecount + " number of unvisited locations " + (15 - visited.size());
            life.setText(text);
        } else {
            String text = "You are dead";
            life.setText(text);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (visited.size() > 14 && currentLocation == siebel) {
            String text = "you win";
            life.setText(text);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }




        options.clearCheck();


        quit.setOnClickListener(view ->  {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            toReturn.set(true);
        });
        north.setVisibility(View.VISIBLE);
        south.setVisibility(View.VISIBLE);
        east.setVisibility(View.VISIBLE);
        west.setVisibility(View.VISIBLE);
        description.setText(currentLocation.getDescription());


        if (!currentLocation.getExits().contains("north")) {
            north.setVisibility(View.GONE);
        }
        if (!currentLocation.getExits().contains("east")) {
            east.setVisibility(View.GONE);
        }
        if (!currentLocation.getExits().contains("south")) {
            south.setVisibility(View.GONE);
        }
        if (!currentLocation.getExits().contains("west")) {
            west.setVisibility(View.GONE);
        }
        RequestQueue mQueue = Volley.newRequestQueue(this);
        System.out.println("pff");
        System.out.println(currentLocation.getData());
        System.out.println(visited.size());
        for (int i = 0; i < visited.size(); i++) {
            System.out.println(visited.get(i));
        }


        if (!visited.contains(currentLocation.getData())) {
            System.out.println("loop1");
            String url = "https://opentdb.com/api.php?amount=15&difficulty=medium&type=multiple";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        System.out.println("loop33");
                        JSONArray questions = response.getJSONArray("results");
                        JSONObject oneQuestion = questions.getJSONObject(currentLocation.getData());
                        String q = oneQuestion.getString("question");
                        correct_answer[0] = oneQuestion.getString("correct_answer");
                        JSONArray ic = oneQuestion.getJSONArray("incorrect_answers");
                        List<String> ics = new ArrayList<>();
                        for (int i = 0; i < ic.length(); i++) {
                            ics.add(ic.getString(i));
                        }
                        question.setText(q);
                        int i = rand.nextInt(4);
                        switch (i) {
                            case 0:
                                ansA.setText(correct_answer[0]);
                                ansB.setText(ics.get(0));
                                ansC.setText(ics.get(1));
                                ansD.setText(ics.get(2));
                                break;
                            case 1:
                                ansA.setText(ics.get(0));
                                ansB.setText(correct_answer[0]);
                                ansC.setText(ics.get(1));
                                ansD.setText(ics.get(2));
                                break;
                            case 2:
                                ansA.setText(ics.get(0));
                                ansB.setText(ics.get(1));
                                ansC.setText(correct_answer[0]);
                                ansD.setText(ics.get(2));
                                break;
                            case 3:
                                ansA.setText(ics.get(2));
                                ansB.setText(ics.get(0));
                                ansC.setText(ics.get(1));
                                ansD.setText(correct_answer[0]);
                                break;
                        }
                        north.setOnClickListener(view -> {
                            if (currentLocation.getExit("north") != null && visited.contains(currentLocation.getExit("north").getData())) {
                                goTo("north");
                            }
                        });
                        south.setOnClickListener(view -> {
                            if (currentLocation.getExit("south") != null && visited.contains(currentLocation.getExit("south").getData())) {
                                goTo("south");
                            }
                        });
                        east.setOnClickListener(view -> {
                            if (currentLocation.getExit("east") != null && visited.contains(currentLocation.getExit("east").getData())) {
                                goTo("east");
                            }
                        });
                        west.setOnClickListener(view -> {
                            if (currentLocation.getExit("west") != null && visited.contains(currentLocation.getExit("west").getData())) {
                                goTo("west");
                            }
                        });
                        optA.setOnClickListener(view -> {

                            if (optA.isChecked()) {
                                if (ansA.getText().equals(correct_answer[0])) {
                                    visited.add(currentLocation.getData());
                                    mediaPlayer.start();
                                    processAction();
                                } else {
                                    lifecount--;
                                    processAction();
                                }
                            }
                        });
                        optD.setOnClickListener(view -> {

                            if (optD.isChecked()) {
                                if (ansD.getText().equals(correct_answer[0])) {
                                    visited.add(currentLocation.getData());
                                    mediaPlayer.start();
                                    processAction();
                                } else {
                                    lifecount--;
                                    processAction();
                                }
                            }
                        });
                        optB.setOnClickListener(view -> {

                            if (optB.isChecked()) {
                                if (ansB.getText().equals(correct_answer[0])) {
                                    visited.add(currentLocation.getData());
                                    mediaPlayer.start();
                                    processAction();
                                } else {
                                    lifecount--;
                                    processAction();
                                }
                            }
                        });
                        optC.setOnClickListener(view -> {
                            if (optC.isChecked()) {
                                if (ansC.getText().equals(correct_answer[0])) {
                                    visited.add(currentLocation.getData());
                                    mediaPlayer.start();
                                    processAction();
                                } else {
                                    lifecount--;
                                    processAction();
                                }
                            }
                        });
                    } catch (JSONException error) {
                        //response
                    }
                }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Oh No");

                        }
                    });
            mQueue.add(jsonObjectRequest);

        } else {
            System.out.println("loop2");
            question.setVisibility(View.GONE);
            optA.setVisibility(View.GONE);
            optB.setVisibility(View.GONE);
            optC.setVisibility(View.GONE);
            optD.setVisibility(View.GONE);
            ansA.setVisibility(View.GONE);
            ansB.setVisibility(View.GONE);
            ansC.setVisibility(View.GONE);
            ansD.setVisibility(View.GONE);
            north.setOnClickListener(view -> {
                goTo("north");
            });
            west.setOnClickListener(view -> {
                goTo("west");
            });
            south.setOnClickListener(view -> {
                goTo("south");
            });
            east.setOnClickListener(view -> {
                goTo("east");
            });
        }
        count++;
        System.out.println(count);

        if ((currentLocation == siebel && visited.size() == 14)  || count > 1) {
            System.out.println("You win!");
            return true;
        }

        return toReturn.get();
    }
    public void goTo(String direction) {

        //Leaving current location to go to another location
        currentLocation = currentLocation.getExit(direction);
        processAction();

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
            //currentLocation.removeItem(item);
            inventory.add(newItem);
            System.out.println("picked up: " + item);
        }
    }
}
