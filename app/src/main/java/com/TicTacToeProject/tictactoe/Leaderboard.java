package com.TicTacToeProject.tictactoe;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leaderboard extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        databaseHelper = new DatabaseHelper(this);

        PieChart pieChart = findViewById(R.id.pieChart);
        BarChart barChart = findViewById(R.id.barChart);

        Map<String, Integer> playerScores = getPlayerScores();

        List<PieEntry> pieEntries = new ArrayList<>();
        List<BarEntry> barEntries = new ArrayList<>();
        int index = 0;

        for (Map.Entry<String, Integer> entry : playerScores.entrySet()) {
            pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
            barEntries.add(new BarEntry(index++, entry.getValue()));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Player Scores");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate(); // refresh

        BarDataSet barDataSet = new BarDataSet(barEntries, "Player Scores");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate(); // refresh

        Description description = new Description();
        description.setText("Player Leaderboard");
        pieChart.setDescription(description);
        barChart.setDescription(description);
    }

    private Map<String, Integer> getPlayerScores() {
        Map<String, Integer> scores = new HashMap<>();
        Cursor cursor = databaseHelper.getAllResults();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String playerOne = cursor.getString(cursor.getColumnIndex("player_one"));
                String playerTwo = cursor.getString(cursor.getColumnIndex("player_two"));
                String winner = cursor.getString(cursor.getColumnIndex("winner"));

                if (!scores.containsKey(playerOne)) {
                    scores.put(playerOne, 0);
                }
                if (!scores.containsKey(playerTwo)) {
                    scores.put(playerTwo, 0);
                }

                if (winner.equals("Draw")) {
                    scores.put(playerOne, scores.get(playerOne) + 1);
                    scores.put(playerTwo, scores.put(playerTwo, scores.get(playerTwo) + 1));
                } else {
                    scores.put(winner, scores.get(winner) + 3);
                }
            }
            cursor.close();
        }
        return scores;
    }
}
