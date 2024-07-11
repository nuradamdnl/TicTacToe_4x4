package com.TicTacToeProject.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.TicTacToeProject.tictactoe.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        startService(new Intent(this, BackgroundMusicService.class));

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = binding.signupNickname.getText().toString();
                String username = binding.signupUsername.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();

                if (nickname.equals("") || username.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(SignupActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        Boolean checkUserUsername = databaseHelper.checkUsername(username);
                        if (checkUserUsername == false) {
                            Boolean insert = databaseHelper.insertData(nickname, username, password);
                            if (insert == true) {
                                Toast.makeText(SignupActivity.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignupActivity.this, "Signup Failed!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignupActivity.this, "User already exists! Please login", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
