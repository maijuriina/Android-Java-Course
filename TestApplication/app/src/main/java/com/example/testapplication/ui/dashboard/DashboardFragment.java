package com.example.testapplication.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DashboardFragment extends Fragment implements View.OnClickListener, LocationListener {

    private DashboardViewModel dashboardViewModel;
    LocationManager locationManager;
    Location lastLocation;
    TextInputLayout latitude; // introduce xml's textinputlayouts
    TextInputLayout longitude;
    TextInputLayout address;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111; // giving a number value for the request in question

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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        // provider may be GPS_PROVIDER | NETWORK_PROVIDER | PASSIVE_PROVIDER
        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        setCoordinates(lastLocation);
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
        // tähän vaihtuvat koordinaatit

    }

    @Override
    public void onClick(View view) {

    }

    public void setCoordinates(Location l) {
        latitude.setHint(String.valueOf(l.getLatitude()));
        longitude.setHint(String.valueOf(l.getLongitude()));
    }
}