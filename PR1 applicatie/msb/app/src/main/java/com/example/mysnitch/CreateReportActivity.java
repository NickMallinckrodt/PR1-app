package com.example.mysnitch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.ULocale;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.example.mysnitch.database.AppRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CreateReportActivity extends AppCompatActivity {

    EditText reportTitle;
    EditText reportDiscription;
    EditText licensePlate;
    ImageView image;
    Button createReport;
    Button backToMenu;
    TextView statusMessage;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView latTextView, lonTextView, locAddress;
    String myCity;
    ByteArrayOutputStream istream = new ByteArrayOutputStream();
    byte[] imageByteArray;
    Bitmap selectedImage;
    Media media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        final AppRepository appRepository = new AppRepository(getApplicationContext());

        image = findViewById(R.id.imageView2);
        reportTitle = findViewById(R.id.reportTitle);
        reportDiscription = findViewById(R.id.reportDiscription);
        licensePlate = findViewById(R.id.reportLicensePlate);
        createReport = findViewById(R.id.createReportButton);
        backToMenu = findViewById(R.id.backToMenu);
        statusMessage = findViewById(R.id.statusMessage);
        latTextView = findViewById(R.id.Latitude);
        lonTextView = findViewById(R.id.Longitude);
        locAddress = findViewById(R.id.Address);

        image.setImageResource(R.drawable.choose_image);


        createReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reportTitle.getText().toString().length() > 0 &&
                        reportDiscription.getText().toString().length() > 0 &&
                        licensePlate.getText().toString().length() > 0 &&
                        lonTextView.getText().toString().length() > 0 &&
                        latTextView.getText().toString().length() > 0 &&
                        locAddress.getText().toString().length() > 0
                ) {

                    media = new Media(imageByteArray);
                    //Log.d("IMAGE: ", Integer.toString(imageByteArray[0]) + Integer.toString(imageByteArray[1]) + Integer.toString(imageByteArray[2]));

                    try {
                        appRepository.insertReport(reportTitle.getText().toString(), reportDiscription.getText().toString(), licensePlate.getText().toString().toUpperCase(), lonTextView.getText().toString(), latTextView.getText().toString(), locAddress.getText().toString(), media);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    reportTitle.setText("");
                    reportDiscription.setText("");
                    licensePlate.setText("");
                    image.setImageResource(R.drawable.choose_image);


                    //Report.addReport( new Report( reportTitle.getText().toString(), reportDiscription.getText().toString(), licensePlate.getText().toString() ) );
                    statusMessage.setText("Succesfully created report!");
                }

            }
        });

        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateReportActivity.this, MenuActivity.class));
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(CreateReportActivity.this);
            }


        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        selectedImage = (Bitmap) data.getExtras().get("data");

                        image.setImageBitmap(selectedImage);
                        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, istream);
                        imageByteArray = istream.toByteArray();
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri imageUri = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (imageUri != null) {
                            Cursor cursor = getContentResolver().query(imageUri,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                try {
                                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, istream);
                                imageByteArray = istream.toByteArray();
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latTextView.setText(location.getLatitude() + "");
                                    lonTextView.setText(location.getLongitude() + "");

                                    getCityname(location.getLatitude(), location.getLongitude());
                                    locAddress.setText(myCity);
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latTextView.setText(mLastLocation.getLatitude() + "");
            lonTextView.setText(mLastLocation.getLongitude() + "");
            getCityname(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            locAddress.setText(myCity);
        }
    };

    private String getCityname(double lat, double lon) {
        Geocoder geocoder = new Geocoder(CreateReportActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            String address = addresses.get(0).getAddressLine(0);
            myCity = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCity;
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }


    }
}


