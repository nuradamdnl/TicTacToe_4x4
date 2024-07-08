package com.TicTacToeProject.tictactoe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.TicTacToeProject.tictactoe.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = new int[16]; //16 zeroes
    private int playerTurn = 1;
    private int totalSelectedBoxes = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Add winning combinations for 4x4 grid
        // Rows
        combinationList.add(new int[]{0, 1, 2, 3});
        combinationList.add(new int[]{4, 5, 6, 7});
        combinationList.add(new int[]{8, 9, 10, 11});
        combinationList.add(new int[]{12, 13, 14, 15});
        // Columns
        combinationList.add(new int[]{0, 4, 8, 12});
        combinationList.add(new int[]{1, 5, 9, 13});
        combinationList.add(new int[]{2, 6, 10, 14});
        combinationList.add(new int[]{3, 7, 11, 15});
        // Diagonals
        combinationList.add(new int[]{0, 5, 10, 15});
        combinationList.add(new int[]{3, 6, 9, 12});

        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");
        binding.playerOneName.setText(getPlayerOneName);
        binding.playerTwoName.setText(getPlayerTwoName);

        // Set click listeners for all 16 image views
        binding.image1.setOnClickListener(view -> onClickImage(view, 0));
        binding.image2.setOnClickListener(view -> onClickImage(view, 1));
        binding.image3.setOnClickListener(view -> onClickImage(view, 2));
        binding.image4.setOnClickListener(view -> onClickImage(view, 3));
        binding.image5.setOnClickListener(view -> onClickImage(view, 4));
        binding.image6.setOnClickListener(view -> onClickImage(view, 5));
        binding.image7.setOnClickListener(view -> onClickImage(view, 6));
        binding.image8.setOnClickListener(view -> onClickImage(view, 7));
        binding.image9.setOnClickListener(view -> onClickImage(view, 8));
        binding.image10.setOnClickListener(view -> onClickImage(view, 9));
        binding.image11.setOnClickListener(view -> onClickImage(view, 10));
        binding.image12.setOnClickListener(view -> onClickImage(view, 11));
        binding.image13.setOnClickListener(view -> onClickImage(view, 12));
        binding.image14.setOnClickListener(view -> onClickImage(view, 13));
        binding.image15.setOnClickListener(view -> onClickImage(view, 14));
        binding.image16.setOnClickListener(view -> onClickImage(view, 15));
    }

    private void onClickImage(View view, int position) {
        if (isBoxSelectable(position)) {
            performAction((ImageView) view, position);
        }
    }

    private void performAction(ImageView imageView, int selectedBoxPosition) {
        boxPositions[selectedBoxPosition] = playerTurn;
        imageView.setImageResource(playerTurn == 1 ? R.drawable.ximage : R.drawable.oimage);

        if (checkResults()) {
            String winner = playerTurn == 1 ? binding.playerOneName.getText().toString() : binding.playerTwoName.getText().toString();
            ResultDialog resultDialog = new ResultDialog(MainActivity.this, winner + " is a Winner!", MainActivity.this);
            resultDialog.setCancelable(false);
            resultDialog.show();
        } else if (totalSelectedBoxes == 16) {
            ResultDialog resultDialog = new ResultDialog(MainActivity.this, "Match Draw", MainActivity.this);
            resultDialog.setCancelable(false);
            resultDialog.show();
        } else {
            changePlayerTurn(playerTurn == 1 ? 2 : 1);
            totalSelectedBoxes++;
        }
    }

    private void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            binding.playerOneLayout.setBackgroundResource(R.drawable.black_border);
            binding.playerTwoLayout.setBackgroundResource(R.drawable.white_box);
        } else {
            binding.playerTwoLayout.setBackgroundResource(R.drawable.black_border);
            binding.playerOneLayout.setBackgroundResource(R.drawable.white_box);
        }
    }

    private boolean checkResults() {
        for (int[] combination : combinationList) {
            if (boxPositions[combination[0]] == playerTurn &&
                    boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn &&
                    boxPositions[combination[3]] == playerTurn) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoxSelectable(int boxPosition) {
        return boxPositions[boxPosition] == 0;
    }

    public void restartMatch() {
        boxPositions = new int[16]; // Reset to 16 zeroes
        playerTurn = 1;
        totalSelectedBoxes = 1;

        // Reset all images to the initial state
        binding.image1.setImageResource(R.drawable.white_box);
        binding.image2.setImageResource(R.drawable.white_box);
        binding.image3.setImageResource(R.drawable.white_box);
        binding.image4.setImageResource(R.drawable.white_box);
        binding.image5.setImageResource(R.drawable.white_box);
        binding.image6.setImageResource(R.drawable.white_box);
        binding.image7.setImageResource(R.drawable.white_box);
        binding.image8.setImageResource(R.drawable.white_box);
        binding.image9.setImageResource(R.drawable.white_box);
        binding.image10.setImageResource(R.drawable.white_box);
        binding.image11.setImageResource(R.drawable.white_box);
        binding.image12.setImageResource(R.drawable.white_box);
        binding.image13.setImageResource(R.drawable.white_box);
        binding.image14.setImageResource(R.drawable.white_box);
        binding.image15.setImageResource(R.drawable.white_box);
        binding.image16.setImageResource(R.drawable.white_box);
    }
}