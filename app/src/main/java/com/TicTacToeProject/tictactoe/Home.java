package com.TicTacToeProject.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    String currentUsername;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        currentUsername = intent.getStringExtra("username");

        // Find the TextView and set the username
        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Welcome \n" + currentUsername);

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
                intent.putExtra("username", currentUsername); // Pass the username to UserProfile
                startActivity(intent);
            }
        });

        // Add more code as needed for other functionality or UI setup
    }
}
