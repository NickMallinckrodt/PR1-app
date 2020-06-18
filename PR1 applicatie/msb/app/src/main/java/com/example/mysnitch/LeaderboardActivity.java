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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LeaderboardActivity extends AppCompatActivity
{
    LinearLayout leaderboardLayout;

    Button backToMenuFromLeaderboard;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        AppRepository appRepository = new AppRepository(getApplicationContext());
        List<User> users = null;

        // Gets all vehicles from database in a List
        try {
            users = appRepository.getAllUsers();
            Log.d( "test", Integer.toString(users.size()) );
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        leaderboardLayout = findViewById(R.id.leaderboardLayout);

        backToMenuFromLeaderboard = findViewById(R.id.backToMenuFromLeaderboard);
        backToMenuFromLeaderboard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LeaderboardActivity.this, MenuActivity.class));
            }
        });
        // addes parameter for regular List to convert to ArrayList
        addContent(users);
    }

    private void addContent(List<User> allUsers)
    {
        // converts List of vehicles to ArrayList of vehicles
        ArrayList<User> unorderedUsers = new ArrayList<User>(allUsers);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );

        ArrayList<User> orderedUsers = orderUsers(unorderedUsers);

        for (int i = 0; i < orderedUsers.size(); i++)
        {
            TextView user = new TextView(this);
            user.setLayoutParams(layoutParams);
            user.setText((i + 1) + ": " + orderedUsers.get(i).getUsername() + " - " + "Snitchscore: " + orderedUsers.get(i).getSnitchScore());
            user.setBackgroundColor(0xff_ebebeb);
            user.setWidth(user.getMaxWidth());
            leaderboardLayout.addView(user);
        }
    }

    private ArrayList<User> orderUsers(ArrayList<User> unorderedUsers )
    {
        // create copy of the vehicle ArrayList so it does not actually remove all vehicles from Vehicle.vehicles
        ArrayList<User> unorderedUsersCopy = new ArrayList<>();
        for( User user : unorderedUsers )
            unorderedUsersCopy.add(user);

        ArrayList<User> orderedUsers = new ArrayList<>();

        while(unorderedUsersCopy.size() > 0)
        {
            int indexOfBiggest = 0;
            int biggest = unorderedUsersCopy.get(0).getSnitchScore();

            for (int i = 0; i < unorderedUsersCopy.size(); i++)
            {
                if( unorderedUsersCopy.get(i).getSnitchScore() > biggest )
                {
                    indexOfBiggest = i;
                    biggest = unorderedUsersCopy.get(i).getSnitchScore();
                }
            }

            orderedUsers.add(unorderedUsersCopy.get(indexOfBiggest));
            unorderedUsersCopy.remove(indexOfBiggest);
        }

        return orderedUsers;
    }
}
