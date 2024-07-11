package com.TicTacToeProject.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardRecordTable extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboardrecordtable);

        databaseHelper = new DatabaseHelper(this);
        tableLayout = findViewById(R.id.tableLayout);

        Button backButton = findViewById(R.id.ChartBackButton);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(LeaderboardRecordTable.this, Leaderboard.class);
            startActivity(intent);
        });

        loadTableData();
    }

    private void loadTableData() {
        Cursor cursor = databaseHelper.getAllResults();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.table_row, null);
                ((TextView) row.findViewById(R.id.gameId)).setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
                ((TextView) row.findViewById(R.id.playerOne)).setText(cursor.getString(cursor.getColumnIndex("player_one")));
                ((TextView) row.findViewById(R.id.playerTwo)).setText(cursor.getString(cursor.getColumnIndex("player_two")));
                ((TextView) row.findViewById(R.id.winner)).setText(cursor.getString(cursor.getColumnIndex("winner")));

                Button editButton = row.findViewById(R.id.editButton);
                Button deleteButton = row.findViewById(R.id.deleteButton);

                int gameId = cursor.getInt(cursor.getColumnIndex("id"));
                editButton.setOnClickListener(view -> showEditDialog(gameId));
                deleteButton.setOnClickListener(view -> showDeleteDialog(gameId));

                tableLayout.addView(row);
            }
            cursor.close();
        }
    }

    private void showEditDialog(int gameId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_record, null);

        EditText editPlayerOne = view.findViewById(R.id.editPlayerOne);
        EditText editPlayerTwo = view.findViewById(R.id.editPlayerTwo);
        EditText editWinner = view.findViewById(R.id.editWinner);

        Cursor cursor = databaseHelper.getAllResults();
        if (cursor != null && cursor.moveToFirst()) {
            editPlayerOne.setText(cursor.getString(cursor.getColumnIndex("player_one")));
            editPlayerTwo.setText(cursor.getString(cursor.getColumnIndex("player_two")));
            editWinner.setText(cursor.getString(cursor.getColumnIndex("winner")));
            cursor.close();
        }

        builder.setView(view)
                .setTitle("Edit Record")
                .setPositiveButton("Update", (dialog, which) -> {
                    String playerOne = editPlayerOne.getText().toString();
                    String playerTwo = editPlayerTwo.getText().toString();
                    String winner = editWinner.getText().toString();
                    boolean updated = databaseHelper.updateResult(gameId, playerOne, playerTwo, winner);
                    if (updated) {
                        Toast.makeText(this, "Record updated", Toast.LENGTH_SHORT).show();
                        recreate(); // Refresh activity
                    } else {
                        Toast.makeText(this, "Error updating record", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void showDeleteDialog(int gameId) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Record")
                .setMessage("Are you sure you want to delete this record?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    boolean deleted = databaseHelper.deleteResult(gameId);
                    if (deleted) {
                        Toast.makeText(this, "Record deleted", Toast.LENGTH_SHORT).show();
                        recreate(); // Refresh activity
                    } else {
                        Toast.makeText(this, "Error deleting record", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
