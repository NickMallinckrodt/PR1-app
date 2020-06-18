package com.example.mysnitch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mysnitch.database.AppRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SchandpaalActivity extends AppCompatActivity
{
    LinearLayout schandpaalLayout;

    Button backToMenuFromSchandpaal;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schandpaal);
        AppRepository appRepository = new AppRepository(getApplicationContext());
        List<Vehicle> vehicles = null;

        // Gets all vehicles from database in a List
        try {
            vehicles = appRepository.getAllVehicles();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        schandpaalLayout = findViewById(R.id.schandpaalLayout);

        backToMenuFromSchandpaal = findViewById(R.id.backToMenuFromSchandpaal);
        backToMenuFromSchandpaal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(SchandpaalActivity.this, MenuActivity.class));
            }
        });
        // addes parameter for regular List to convert to ArrayList
        addContent(vehicles);
    }

    private void addContent(List<Vehicle> allVehicles)
    {
        // converts List of vehicles to ArrayList of vehicles
        ArrayList<Vehicle> unorderedVehicles = new ArrayList<Vehicle>(allVehicles);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );

        ArrayList<Vehicle> orderedVehicles = orderVehicles(unorderedVehicles); // TODO Vehicle.getVehicles() should be loaded from database

        for (int i = 0; i < orderedVehicles.size(); i++)
        {
            TextView reportTitle = new TextView(this);
            reportTitle.setLayoutParams(layoutParams);
            reportTitle.setText((i + 1) + ": " + orderedVehicles.get(i).getLicensePlate() + ", " + orderedVehicles.get(i).getTimesReported() + " times");
            reportTitle.setBackgroundColor(0xff_ebebeb);
            reportTitle.setWidth(reportTitle.getMaxWidth());
            schandpaalLayout.addView(reportTitle);
        }
    }

    private ArrayList<Vehicle> orderVehicles( ArrayList<Vehicle> unorderedVehicles )
    {
        // create copy of the vehicle ArrayList so it does not actually remove all vehicles from Vehicle.vehicles
        ArrayList<Vehicle> unorderedVehiclesCopy = new ArrayList<>();
        for( Vehicle vehicle : unorderedVehicles )
            unorderedVehiclesCopy.add(vehicle);

        ArrayList<Vehicle> orderedVehicles = new ArrayList<>();

        while(unorderedVehiclesCopy.size() > 0)
        {
            int indexOfBiggest = 0;
            int biggest = unorderedVehiclesCopy.get(0).getTimesReported();

            for (int i = 0; i < unorderedVehiclesCopy.size(); i++)
            {
                if( unorderedVehiclesCopy.get(i).getTimesReported() > biggest )
                {
                    indexOfBiggest = i;
                    biggest = unorderedVehiclesCopy.get(i).getTimesReported();
                }
            }

            orderedVehicles.add(unorderedVehiclesCopy.get(indexOfBiggest));
            unorderedVehiclesCopy.remove(indexOfBiggest);
        }

        return orderedVehicles;
    }
}
