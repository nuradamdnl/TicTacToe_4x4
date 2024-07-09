package com.TicTacToeProject.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button playNowButton = findViewById(R.id.PlayNowButton);
        playNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AddPlayers.class);
                startActivity(intent);
            }
        });

        Button leaderboardButton = findViewById(R.id.LeaderboardButton);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Leaderboard.class);
                startActivity(intent);
            }
        });

        Button UserProfileButton = findViewById(R.id.UserProfileButton);
        UserProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, UserProfile.class);
                startActivity(intent);
            }
        });

        // Add more code as needed for other functionality or UI setup

    }

    // If you have more methods or overrides, they would go here

}