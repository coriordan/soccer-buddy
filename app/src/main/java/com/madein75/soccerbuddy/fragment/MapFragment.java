package com.madein75.soccerbuddy.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.madein75.soccerbuddy.R;
import com.madein75.soccerbuddy.SoccerBuddyApplication;

import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportMapFragment implements
        GoogleMap.OnMapClickListener,
        OnMapReadyCallback {

    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private GoogleMap map;
    private long UPDATE_INTERVAL = 5000; /* 5 secs */
    private long FASTEST_INTERVAL = 1000; /* 1 sec */
    private float                       zoom = 13f;
    private LatLng location;
    private boolean readOnly = false;


    private static final int PERMISSION_REQUEST_CODE = 200;

    public SoccerBuddyApplication app = SoccerBuddyApplication.getInstance();

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        try {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            createLocationCallback();
            createLocationRequest();
        }
        catch(SecurityException se) {
            Toast.makeText(getActivity(),"Check Your Permissions",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        initListeners();
        if(checkPermission()) {
            map.setMyLocationEnabled(true);
            initCamera(app.currentLocation);
        }
        else if (!checkPermission()) {
            requestPermission();
        }

        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted) {
                        Snackbar.make(getView(), "Permission Granted, Now you can access location data.",
                                Snackbar.LENGTH_LONG).show();
                        if(checkPermission())
                            map.setMyLocationEnabled(true);
                        startLocationUpdates();
                    }
                    else {

                        Snackbar.make(getView(), "Permission Denied, You cannot access location data.",
                                Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    /* Creates a callback for receiving location events.*/
    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                app.currentLocation = locationResult.getLastLocation();
                initCamera(app.currentLocation);
            }
        };
    }

    public void initListeners() {
        map.setOnMapClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMapAsync(this);

        if (checkPermission()) {
            if (app.currentLocation != null) {
                Toast.makeText(getActivity(), "GPS location was found!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Current location was null, Setting Default Values!", Toast.LENGTH_SHORT).show();
                app.currentLocation = new Location("Waterford City Default (WIT)");
                app.currentLocation.setLatitude(52.2462);
                app.currentLocation.setLongitude(-7.1202);
            }
            if(map != null) {
                initCamera(app.currentLocation);
                map.setMyLocationEnabled(true);
            }
            startLocationUpdates();
        }
        else if (!checkPermission()) {
            requestPermission();
        }
    }

    private void initCamera(Location location) {
        if (zoom != 13f && zoom != map.getCameraPosition().zoom)
            zoom = map.getCameraPosition().zoom;

        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(),
                        location.getLongitude()))
                .zoom(zoom).bearing(0.0f)
                .tilt(0.0f).build();

        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);
    }

    public void startLocationUpdates() {
        try {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                    locationCallback, Looper.myLooper());
        }
        catch(SecurityException se) {
            Toast.makeText(getActivity(),"Check Your Permissions on Location Updates",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (!readOnly) {
            map.clear();
            addMapLocation(latLng);
            this.location = latLng;
        }
    }

    public void setReadOnly() { readOnly = true; }

    public LatLng getMarkedLocation() {
        return location;
    }

    public void setMarkedLocation(LatLng location) {
        addMapLocation(location);
    }

    private void addMapLocation(LatLng location) {
        MarkerOptions marker = new MarkerOptions().position(location);
        map.addMarker(marker);
    }

}
