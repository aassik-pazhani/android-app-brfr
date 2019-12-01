package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Room extends AppCompatActivity {


    private Location currentLocation;
    List<Location> visited = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.room);
        Location union, mainQuad, ugl, ike, lar, par, chemAnnex, church, altgeld, engHall, loomis, grainger, siebel, northQuad, ece;

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
        currentLocation = union;
        gameplay();

    }

    private void gameplay() {
        boolean finished = false;
        while (!finished) {
            finished = processAction();
        }
    }

    public boolean processAction() {
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
        Button inventory_butt = findViewById(R.id.inventorybutton);

        inventory_butt.setOnClickListener(view -> {
            Intent intent = new Intent(this, Inventory.class);
            startActivity(intent);
        });

        quit.setOnClickListener(view ->  {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            toReturn.set(true);
        });

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

        if (!visited.contains(currentLocation)) {
            String url = "https://opentdb.com/api.php?amount=15&category=18&difficulty=medium&type=multiple";
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                JSONArray questions = response.getJSONArray(1);
                                JSONObject oneQuestion = questions.getJSONObject(1);
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
                                    case 0: ansA.setText(correct_answer[0]);
                                            ansB.setText(ics.get(0));
                                            ansC.setText(ics.get(1));
                                            ansD.setText(ics.get(2));
                                            break;
                                    case 1: ansA.setText(ics.get(0));
                                            ansB.setText(correct_answer[0]);
                                            ansC.setText(ics.get(1));
                                            ansD.setText(ics.get(2));
                                            break;
                                    case 2: ansA.setText(ics.get(0));
                                            ansB.setText(ics.get(1));
                                            ansC.setText(correct_answer[0]);
                                            ansD.setText(ics.get(2));
                                            break;
                                    case 3: ansA.setText(ics.get(2));
                                            ansB.setText(ics.get(0));
                                            ansC.setText(ics.get(1));
                                            ansD.setText(correct_answer[0]);
                                            break;
                                }
                                if (visited.contains(currentLocation.getExit("north"))) {
                                    north.setOnClickListener(view -> {
                                        goTo("north");
                                    });
                                }
                                if (visited.contains(currentLocation.getExit("south"))) {
                                    south.setOnClickListener(view -> {
                                        goTo("south");
                                    });
                                }
                                if (visited.contains(currentLocation.getExit("east"))) {
                                    east.setOnClickListener(view -> {
                                        goTo("east");
                                    });
                                }
                                if (visited.contains(currentLocation.getExit("west"))) {
                                    west.setOnClickListener(view -> {
                                        goTo("west");
                                    });
                                }
                                if (optA.isChecked()) {
                                    if (ansA.getText().equals(correct_answer[0])) {
                                        visited.add(currentLocation);
                                    }
                                }
                                if (optB.isChecked()) {
                                    if (ansB.getText().equals(correct_answer[0])) {
                                        visited.add(currentLocation);
                                    }
                                }
                                if (optC.isChecked()) {
                                    if (ansC.getText().equals(correct_answer[0])) {
                                        visited.add(currentLocation);
                                    }
                                }
                                if (optD.isChecked()) {
                                    if (ansD.getText().equals(correct_answer[0])) {
                                        visited.add(currentLocation);
                                    }
                                }
                                processAction();
                            } catch (JSONException error) {
                                //response
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });
        } else {
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
        return toReturn.get();
    }
    public void goTo(String direction) {

        //Leaving current location to go to another location
        Location nextLocation = currentLocation.getExit(direction);
        if (nextLocation == null) {
            System.out.println("Nothing to the " + direction + " of " + currentLocation + "!");
        } else {
            currentLocation = nextLocation;
            processAction();
            System.out.println(currentLocation.getDescription());
        }
    }
}
