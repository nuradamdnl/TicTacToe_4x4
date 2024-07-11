package com.TicTacToeProject.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    TextView textViewNickname, textViewUsername, textViewPassword;
    DatabaseHelper databaseHelper;
    String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        startService(new Intent(this, BackgroundMusicService.class));

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize TextViews
        textViewNickname = findViewById(R.id.textViewNickname);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewPassword = findViewById(R.id.textViewPassword);

        // Get the username passed from the previous activity
        currentUsername = getIntent().getStringExtra("username");

        // Call method to display user details
        displayUserDetails(currentUsername);

        // Setup EditProfileButton click listener
        Button EditProfileButton = findViewById(R.id.EditProfileButton);
        EditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, EditProfile.class);
                intent.putExtra("username", currentUsername);
                startActivity(intent);
            }
        });

        // Setup EditProfileButton click listener
        Button userprofileBackButton = findViewById(R.id.UserProfileBackButton);
        userprofileBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, Home.class);
                intent.putExtra("username", currentUsername);
                startActivity(intent);
            }
        });
    }

    private void displayUserDetails(String username) {
        Cursor cursor = databaseHelper.getUserData(username);
        if (cursor != null && cursor.moveToFirst()) {
            String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            String password = cursor.getString(cursor.getColumnIndex("password"));

            // Display the retrieved values in TextViews
            textViewNickname.setText("Nickname: " + nickname);
            textViewUsername.setText("Username: " + username);
            textViewPassword.setText("Password: " + password);

            cursor.close();
        }
    }
}