package com.TicTacToeProject.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.content.SharedPreferences;

public class Home extends AppCompatActivity {

    String currentUsername;
    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        currentUsername = intent.getStringExtra("username");
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        // Find the TextView and set the username
        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Welcome \n" + currentUsername);

        Button playNowButton = findViewById(R.id.PlayNowButton);
        playNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AddPlayers.class);
                intent.putExtra("username", currentUsername); // Pass the username to UserProfile
                startActivity(intent);
            }
        });

        Button leaderboardButton = findViewById(R.id.LeaderboardButton);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Leaderboard.class);
                intent.putExtra("username", currentUsername); // Pass the username to UserProfile
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

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.putString("username", null);
                editor.apply();

                Intent intent = new Intent(Home.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}