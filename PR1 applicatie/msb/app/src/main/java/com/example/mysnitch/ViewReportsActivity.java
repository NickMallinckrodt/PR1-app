package com.example.mysnitch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mysnitch.database.AppRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ViewReportsActivity extends AppCompatActivity
{

    LinearLayout linearLayout;

    Button goBackToTheMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);
        List<Report> allReports = null;

        AppRepository appRepository = new AppRepository(getApplicationContext());

        try {
            allReports = appRepository.getReports();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        linearLayout = findViewById(R.id.reportsLayout);

        goBackToTheMenuButton = findViewById(R.id.goBackToTheMenuButton);
        goBackToTheMenuButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ViewReportsActivity.this, MenuActivity.class));
            }
        });

        for( Report report : allReports )
        {
            Log.d("REPORTTITLE: ", report.getTitle());
            Log.d("REPORTLP: ", report.getVehicle().getLicensePlate());
            addReportToLayout( report );

        }
    }

    private void addReportToLayout( Report report )
    {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );

        TextView reportTitle = new TextView(this );
        reportTitle.setLayoutParams( layoutParams );
        String rtitle = "Title: " + report.getTitle();
        reportTitle.setText( rtitle );
        reportTitle.setBackgroundColor( 0xffc9c9c9 );
        reportTitle.setWidth(reportTitle.getMaxWidth());

        TextView reportLicensePlate = new TextView(this );
        reportLicensePlate.setLayoutParams( layoutParams );
        String tlp = "License plate: " + report.getVehicle().getLicensePlate();
        reportLicensePlate.setText( tlp );
        reportLicensePlate.setBackgroundColor( 0xff_a8a8a8 );
        reportLicensePlate.setWidth(reportTitle.getMaxWidth());

        LinearLayout.LayoutParams layoutParamsForDescription = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        layoutParamsForDescription.setMargins( 0, 0, 0, 50);

        TextView reportDescription = new TextView(this );
        reportDescription.setLayoutParams( layoutParams );
        String tdescr = "Description: " + report.getDescription();
        reportDescription.setText( tdescr );
        reportDescription.setBackgroundColor( 0xff_888888 );
        reportDescription.setWidth(reportDescription.getMaxWidth());

        TextView reportAddress = new TextView(this );
        reportAddress.setLayoutParams( layoutParams);
        String rAddress = "Address: " + report.getAddress();
        reportAddress.setText( rAddress );
        reportAddress.setBackgroundColor( 0xff_888888 );
        reportAddress.setWidth(reportAddress.getMaxWidth());

        String rImage = "No picture taken.";
        TextView imagetest = new TextView(this );
        imagetest.setLayoutParams( layoutParams);
        if(report.getMedia().getImageByteArray() != null) {
             rImage = "Image: " + report.getMedia().getImageByteArray();
        }
        imagetest.setText( rImage );
        imagetest.setBackgroundColor( 0xff_888888 );
        imagetest.setWidth(reportAddress.getMaxWidth());

        TextView empty = new TextView(this);

        linearLayout.addView( reportTitle );
        linearLayout.addView( reportLicensePlate );
        linearLayout.addView( reportDescription );
        linearLayout.addView( reportAddress);
        linearLayout.addView( imagetest);
        linearLayout.addView(empty);
    }
}
