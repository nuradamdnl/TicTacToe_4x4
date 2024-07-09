package com.TicTacToeProject.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText editTextNickname, editTextUsername, editTextPassword;
    Button buttonSave;
    String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        databaseHelper = new DatabaseHelper(this);

        editTextNickname = findViewById(R.id.editTextNickname);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSave = findViewById(R.id.buttonSave);

        // Get the username passed from the previous activity
        Intent intent = getIntent();
        currentUsername = intent.getStringExtra("username");

        // Load user data
        loadUserData();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserData();
            }
        });
    }

    private void loadUserData() {
        Cursor cursor = databaseHelper.getUserData(currentUsername);
        if (cursor != null && cursor.moveToFirst()) {
            int nicknameIndex = cursor.getColumnIndex("nickname");
            int usernameIndex = cursor.getColumnIndex("username");
            int passwordIndex = cursor.getColumnIndex("password");

            if (nicknameIndex >= 0 && usernameIndex >= 0 && passwordIndex >= 0) {
                String nickname = cursor.getString(nicknameIndex);
                String username = cursor.getString(usernameIndex);
                String password = cursor.getString(passwordIndex);

                editTextNickname.setText(nickname);
                editTextUsername.setText(username);
                editTextPassword.setText(password);
            } else {
                // Handle the case where columns are not found
                Toast.makeText(this, "Error loading user data", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        } else {
            // Handle the case where no user data is found
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserData() {
        String newNickname = editTextNickname.getText().toString();
        String newUsername = editTextUsername.getText().toString();
        String newPassword = editTextPassword.getText().toString();

        boolean isUpdated = databaseHelper.updateUserData(currentUsername, newNickname, newUsername, newPassword);
        if (isUpdated) {
            Toast.makeText(EditProfile.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();

            // Start UserProfile activity
            Intent intent = new Intent(EditProfile.this, UserProfile.class);
            intent.putExtra("username", newUsername); // Pass the updated username
            startActivity(intent);
            finish(); // Finish current activity to prevent going back to it on back press
        } else {
            Toast.makeText(EditProfile.this, "Profile update failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
