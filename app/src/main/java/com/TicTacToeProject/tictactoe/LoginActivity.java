package com.TicTacToeProject.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.TicTacToeProject.tictactoe.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private DatabaseHelper databaseHelper;
    //private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        startService(new Intent(this, BackgroundMusicService.class));

        databaseHelper = new DatabaseHelper(this);
        // sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        /** // Check if user is already logged in
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            String username = sharedPreferences.getString("username", null);
            Intent intent = new Intent(LoginActivity.this, Home.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
            return;
        } **/

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.loginUsername.getText().toString().trim(); // Trim to remove leading/trailing spaces
                String password = binding.loginPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCredentials = databaseHelper.checkUsernamePassword(username, password);

                    if (checkCredentials) {
                        // Save login session
                        //SharedPreferences.Editor editor = sharedPreferences.edit();
                        //editor.putBoolean("isLoggedIn", true);
                        //editor.putString("username", username);
                        //editor.apply();

                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, Home.class);
                        intent.putExtra("username", username); // Pass the username
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
