package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;


import android.widget.Button;



import androidx.appcompat.app.AppCompatActivity;



public class MainGame extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);
        //prints instructions for the game
        Button go = findViewById(R.id.go);
        Button quit = findViewById(R.id.quit);
        Button inventoryButton = findViewById(R.id.inventory);
        quit.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        inventoryButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, Inventory.class);
            startActivity(intent);
        });
        go.setOnClickListener(view -> {
            Intent intent = new Intent(this, Room.class);
            startActivity(intent);
            finish();
        });

        System.out.println("ggwp.");
    }

}
