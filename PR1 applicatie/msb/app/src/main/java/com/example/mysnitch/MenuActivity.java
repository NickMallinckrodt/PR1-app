package com.example.mysnitch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button createReport;
    Button viewReports;
    Button schandpaal;
    Button leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        createReport = findViewById(R.id.goToCreateReport);
        createReport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MenuActivity.this, CreateReportActivity.class));
            }
        });

        viewReports = findViewById(R.id.goToViewReports);
        viewReports.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MenuActivity.this, ViewReportsActivity.class));
            }
        });

        schandpaal = findViewById(R.id.goToSchandpaal);
        schandpaal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MenuActivity.this, SchandpaalActivity.class));
            }
        });

        leaderboard = findViewById(R.id.goToLeaderboardView);
        leaderboard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MenuActivity.this, LeaderboardActivity.class));
            }
        });
    }
}
