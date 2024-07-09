package com.TicTacToeProject.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {
    TextView textViewNickname, textViewUsername, textViewPassword;
    DatabaseHelper databaseHelper;
    String currentUsername;
    String currentNickname;
    String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);


        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize TextViews
        textViewNickname = findViewById(R.id.textViewNickname);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewPassword = findViewById(R.id.textViewPassword);

        // Get the username passed from the previous activity
        currentUsername = getIntent().getStringExtra("username");
        currentNickname = getIntent().getStringExtra("nickname");
        currentPassword = getIntent().getStringExtra("password");


    }

    Button EditProfileButton = findViewById(R.id.EditProfileButton);
        EditProfileButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(UserProfile.this, EditProfile.class);
            intent.putExtra("username", currentUsername); // Pass the username to UserProfile
            startActivity(intent);
        }
    });




}