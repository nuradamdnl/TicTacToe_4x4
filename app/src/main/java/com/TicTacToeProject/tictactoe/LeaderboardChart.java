package com.TicTacToeProject.tictactoe;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import android.graphics.Color;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardChart extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboardchart);

        databaseHelper = new DatabaseHelper(this);

        Button chartbackButton = findViewById(R.id.ChartBackButton);
        chartbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaderboardChart.this, Leaderboard.class);
                startActivity(intent);
            }
        });

        PieChart pieChart = findViewById(R.id.pieChart);
        BarChart barChart = findViewById(R.id.barChart);
        //LineChart lineChart = findViewById(R.id.lineChart);

        Map<String, Integer> playerScores = getPlayerScores();

        List<PieEntry> pieEntries = new ArrayList<>();
        List<BarEntry> barEntries = new ArrayList<>();
        List<Entry> lineEntries = new ArrayList<>();
        int index = 0;

        for (Map.Entry<String, Integer> entry : playerScores.entrySet()) {
            pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
            barEntries.add(new BarEntry(index, entry.getValue()));
            lineEntries.add(new Entry(index++, entry.getValue()));
        }



        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Player Scores");
        // Generate random colors for each entry
        /** List<Integer> pieColors = new ArrayList<>();
        for (int i = 0; i < pieEntries.size(); i++) {
            pieColors.add(getRandomColor());
        }
        pieDataSet.setColors(pieColors); **/
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate(); // refresh

        BarDataSet barDataSet = new BarDataSet(barEntries, "Player Scores");
        /**List<Integer> barColors = new ArrayList<>();
        for (int i = 0; i < barEntries.size(); i++) {
            barColors.add(getRandomColor());
        }
        barDataSet.setColors(barColors); **/
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate(); // refresh

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Player Scores");
        lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        LineData lineData = new LineData(lineDataSet);
        //lineChart.setData(lineData);
        //lineChart.invalidate(); // refresh

        Description description = new Description();
        description.setText("Player Leaderboard");
        pieChart.setDescription(description);
        barChart.setDescription(description);
        //lineChart.setDescription(description);
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
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
                    scores.put(playerTwo, scores.get(playerTwo) + 1);
                } else {
                    scores.put(winner, scores.get(winner) + 3);
                }
            }
            cursor.close();
        }
        return scores;
    }
}
