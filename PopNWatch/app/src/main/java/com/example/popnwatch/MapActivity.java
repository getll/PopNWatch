package com.example.popnwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static String TAG = "MapActivity";
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_map );


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
        geocoder = new Geocoder( this );


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        EditText postalCode = new EditText(this);
        postalCode.setInputType( InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder( this );
        alertDialog.setView(postalCode)
                .setTitle( "Enter Postal Code" )
                .setPositiveButton( "Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            List<Address> addresses = geocoder.getFromLocationName(postalCode.getText().toString().trim(), 1);
                            if (addresses.size() > 0) {
                                Address address = addresses.get(0);
                                LatLng userLocation = new LatLng(address.getLatitude(), address.getLongitude());
                                MarkerOptions markerOptions = new MarkerOptions()
                                        .position(userLocation)
                                        .title("You");
                                mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));

                                LatLng cinemaLocation = new LatLng(45.483506964577366, -73.7929295698415);
                                MarkerOptions markerOptions2 = new MarkerOptions()
                                        .position(cinemaLocation)
                                        .title("PopNWatch Theaters");

                                mMap.addMarker(markerOptions2);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                } );
        AlertDialog dialog = alertDialog.create();
        dialog.show();


    }
}