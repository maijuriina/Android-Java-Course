package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class CompanySearchActivity extends AppCompatActivity {
    private TextView receivedTerm;
    RequestQueue requestQueue; // declare requestQueue to be used by volley
    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // Instantiate the cache; 1MB cap
    Network network = new BasicNetwork(new HurlStack()); // Set up the network to use HttpURLConnection as the HTTP client
    String url = "http://avoindata.prh.fi/bis/v1.fi.json/bis/v1?totalResults=false&maxResults=100&resultsFrom=0&name=Lappeenrannan&companyRegistrationFrom=1900-02-28";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_search);

        receivedTerm = findViewById(R.id.searchTerm);
        findSearchTerm();
    }

    private void findSearchTerm() {
        Bundle extras = getIntent().getExtras();
        Log.e("OSAAAAAAAAAA", String.valueOf(extras));
        if (extras == null) return;
        String terms = extras.getString("searchTerms");
        if (terms != null) {
            Log.e("OSAAAAAAAAAA", terms);
            receivedTerm.setText(terms);
        }
    }

    private void startTheQueue() {
        requestQueue = new RequestQueue(cache, network); // Instantiate the RequestQueue with the cache and network
        requestQueue.start(); // Start the queue
    }

}