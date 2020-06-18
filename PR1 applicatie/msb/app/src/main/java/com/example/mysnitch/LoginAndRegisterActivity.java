package com.example.mysnitch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mysnitch.database.AppRepository;
import com.example.mysnitch.database.UserDao;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginAndRegisterActivity extends AppCompatActivity
{

    EditText username;
    EditText password;

    Button login;
    Button register;

    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);

        // Apprepository manages every function between the app and database
        final AppRepository appRepository = new AppRepository(getApplicationContext());
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        errorMessage = findViewById(R.id.errorMessage);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String usernameInput = username.getText().toString();
                String passwordInput = password.getText().toString();



                try {


                    // Haalt user uit de database met username == usernameinput en checkt
                    if(appRepository.userExists(usernameInput))
                    {
                        // Haalt user uit de database met username == usernameinput en checkt wachtwoord
                        if(appRepository.getUserByName(usernameInput).getPassword().equals(passwordInput))
                        {
                            User.setLoggedInUser(appRepository.getUserByName(usernameInput));
                            startActivity(new Intent(LoginAndRegisterActivity.this, MenuActivity.class));
                        }
                        else
                        {
                            errorMessage.setText("password incorrect");
                        }
                    }
                    else
                    {
                        errorMessage.setText("username does not exist");
                    }


                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String usernameInput = username.getText().toString();
                String passwordInput = password.getText().toString();

                if( true )
                {
                    if( passwordInput.length() >= 6 )
                    {
                        // Creates a new user and inserts it into the database
                        appRepository.insertUser(usernameInput, passwordInput, "");

                        //User.addUser( new User( usernameInput, passwordInput, "" ) );
                        errorMessage.setText( "successfully registered. You can now login" );
                    }
                    else
                    {
                        errorMessage.setText( "password must be at least 6 characters" );
                    }
                }
                else
                {
                    errorMessage.setText( "username already exists" );
                }
            }
        });
    }
}
