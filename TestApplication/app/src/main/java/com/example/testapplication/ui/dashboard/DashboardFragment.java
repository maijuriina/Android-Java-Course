package com.example.testapplication.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment implements View.OnClickListener, LocationListener {

    private DashboardViewModel dashboardViewModel;
    LocationManager locationManager;
    Location lastLocation;
    TextInputLayout latitude; // introduce xml's textinputlayouts
    TextInputLayout longitude;
    TextInputLayout address;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111; // giving a number value for the request in question
    Geocoder geocoder; // class for finding location on map
    List<Address> addresses;
    Button mapButton; // introduce xml's mapButton

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        /*final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        // find locationManager
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        latitude = (TextInputLayout)root.findViewById(R.id.textInputLayout); // find xml counterparts
        longitude = (TextInputLayout)root.findViewById(R.id.textInputLayout2);
        address = (TextInputLayout)root.findViewById(R.id.textInputLayout3);
        mapButton = root.findViewById(R.id.mapButton);
        mapButton.setOnClickListener(this);
        startLocationUpdates();
        return root;
    }

    // automatically generated permission check for location
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION/*, Manifest.permission.ACCESS_COARSE_LOCATION*/},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 100, this);
        // provider may be GPS_PROVIDER | NETWORK_PROVIDER | PASSIVE_PROVIDER
        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        setOnUI(lastLocation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Permission granted!", Toast.LENGTH_SHORT).show();
                }  else {
                    Toast.makeText(getContext(), "Permission denied. Map feature inaccessible without location data.", Toast.LENGTH_LONG).show();
                }
                break;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // here whenever the location changes, the location is changed
        setOnUI(location);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mapButton:
                showOnMap(lastLocation);
        }

    }

    // setCoordinates sets values to the TextInputLayouts hint
    public void setOnUI(Location l) {
        latitude.setHint(String.valueOf(l.getLatitude()));
        longitude.setHint(String.valueOf(l.getLongitude()));
        findOnMap(l); // show address
    }

    // find address on map based on location
    public void findOnMap(Location l) {
        try {
            geocoder = new Geocoder(getContext(), Locale.getDefault());
            addresses = geocoder.getFromLocation(l.getLatitude(), l.getLongitude(), 1); // Here 1 represent maxResults
            if (addresses != null) {
                String addressLine = addresses.get(0).getAddressLine(0); // getAddressLine returns a line of the address
                // numbered by the given index
                String city = addresses.get(0).getLocality();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                address.setHint(addressLine);
            } else {
                address.setHint(getResources().getString(R.string.addressMissing));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showOnMap(Location l) {
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("geo:" + l.getLatitude() + "," + l.getLongitude());

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        if (mapIntent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");

            // Attempt to start an activity that can handle the Intent
            startActivity(mapIntent);
        }

    }
}