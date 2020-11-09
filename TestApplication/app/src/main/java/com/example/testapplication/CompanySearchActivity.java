package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CompanySearchActivity extends AppCompatActivity {
    private TextView receivedTerm;

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

}