package com.example.quizappdmrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.location.Location;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.util.List;
import java.util.Locale;

public class AcLocation extends AppCompatActivity implements LocationListener {



    Button bLocation;
    TextView locationTV;
    LocationManager locationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        locationTV=findViewById(R.id.locationTV);
        bLocation=findViewById(R.id.bLocation);




        bLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Runtime permissions

                if(ContextCompat.checkSelfPermission(AcLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AcLocation.this,new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},100);
                }else{
                    getLocation();
                }
            }
        });
    }
    @SuppressLint("MissingPermission")
    private void getLocation(){

        //to get latitude and longitude
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0, AcLocation.this, Looper.myLooper());



    }

    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(AcLocation.this,""+location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_SHORT).show();

        Geocoder geocoder=new Geocoder(this, Locale.getDefault());
        try{

            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address=addresses.get(0).getAddressLine(0);

            locationTV.setText(address);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }


    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}