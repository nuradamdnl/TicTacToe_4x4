package com.TicTacToeProject.tictactoe;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardRecordTable extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TableLayout rankingTableLayout;
    private TableLayout tableLayout;
    private EditText searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboardrecordtable);

        databaseHelper = new DatabaseHelper(this);
        rankingTableLayout = findViewById(R.id.rankingTableLayout);
        tableLayout = findViewById(R.id.tableLayout);
        searchBox = findViewById(R.id.searchBox);

        Button chartbackButton = findViewById(R.id.ChartBackButton);
        chartbackButton.setOnClickListener(view -> {
            Intent intent = new Intent(LeaderboardRecordTable.this, Leaderboard.class);
            startActivity(intent);
        });

        populateRankingTable("");
        populateTable("");

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                populateRankingTable(charSequence.toString());
                populateTable(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void populateRankingTable(String searchQuery) {
        rankingTableLayout.removeViews(1, Math.max(0, rankingTableLayout.getChildCount() - 1)); // Clear all rows except header
        Map<String, Integer> playerScores = new HashMap<>();

        Cursor cursor = databaseHelper.getAllResults();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String playerOne = cursor.getString(cursor.getColumnIndex("player_one"));
                String playerTwo = cursor.getString(cursor.getColumnIndex("player_two"));
                String winner = cursor.getString(cursor.getColumnIndex("winner"));

                if (!playerScores.containsKey(playerOne)) {
                    playerScores.put(playerOne, 0);
                }
                if (!playerScores.containsKey(playerTwo)) {
                    playerScores.put(playerTwo, 0);
                }

                if (winner.equals("Draw")) {
                    playerScores.put(playerOne, playerScores.get(playerOne) + 1);
                    playerScores.put(playerTwo, playerScores.get(playerTwo) + 1);
                } else {
                    playerScores.put(winner, playerScores.get(winner) + 3);
                }
            }
            cursor.close();
        }

        List<Map.Entry<String, Integer>> playerScoreList = new ArrayList<>(playerScores.entrySet());
        Collections.sort(playerScoreList, (o1, o2) -> o2.getValue().compareTo(o1.getValue())); // Sort by score in descending order

        int rank = 1;
        for (Map.Entry<String, Integer> entry : playerScoreList) {
            if (entry.getKey().toLowerCase().contains(searchQuery.toLowerCase())) {
                TableRow row = new TableRow(this);
                row.addView(createTextView(String.valueOf(rank)));
                row.addView(createTextView(entry.getKey()));
                row.addView(createTextView(String.valueOf(entry.getValue())));
                rankingTableLayout.addView(row);
                rank++;
            }
        }
    }

    private void populateTable(String searchQuery) {
        tableLayout.removeViews(1, Math.max(0, tableLayout.getChildCount() - 1)); // Clear all rows except header
        Cursor cursor = databaseHelper.getAllResults();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String playerOne = cursor.getString(cursor.getColumnIndex("player_one"));
                String playerTwo = cursor.getString(cursor.getColumnIndex("player_two"));
                String winner = cursor.getString(cursor.getColumnIndex("winner"));

                if (playerOne.toLowerCase().contains(searchQuery.toLowerCase()) || playerTwo.toLowerCase().contains(searchQuery.toLowerCase())) {
                    int playerOnePoints = calculatePlayerPoints(playerOne, winner);
                    int playerTwoPoints = calculatePlayerPoints(playerTwo, winner);

                    TableRow row = new TableRow(this);
                    row.addView(createTextView(String.valueOf(id)));
                    row.addView(createTextView(playerOne));
                    row.addView(createTextView(String.valueOf(playerOnePoints)));
                    row.addView(createTextView(playerTwo));
                    row.addView(createTextView(String.valueOf(playerTwoPoints)));
                    row.addView(createTextView(winner));

                    Button deleteButton = new Button(this);
                    deleteButton.setText("Delete");
                    deleteButton.setOnClickListener(v -> deleteRecord(id));

                    row.addView(deleteButton);

                    tableLayout.addView(row);
                }
            }
            cursor.close();
        }
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(10, 10, 10, 10);
        return textView;
    }

    private void deleteRecord(int id) {
        boolean result = databaseHelper.deleteResult(id);
        if (result) {
            Toast.makeText(this, "Record deleted", Toast.LENGTH_SHORT).show();
            populateRankingTable(searchBox.getText().toString());
            populateTable(searchBox.getText().toString()); // Refresh the table to show the updated results
        } else {
            Toast.makeText(this, "Error deleting record", Toast.LENGTH_SHORT).show();
        }
    }

    private int calculatePlayerPoints(String playerName, String winner) {
        if (winner.equals("Draw")) {
            return 1;
        } else if (winner.equals(playerName)) {
            return 3;
        } else {
            return 0;
        }
    }
}
