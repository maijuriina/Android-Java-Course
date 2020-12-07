package com.example.testapplication.ui.search;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapplication.CompanySearchActivity;
import com.example.testapplication.GameActivity;
import com.example.testapplication.R;

public class SearchFragment extends Fragment implements View.OnClickListener {
    private EditText searchCompany; // introduce elements
    private Button searchButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        searchCompany = root.findViewById(R.id.searchField); // pair corresponding xml elements
        searchButton = root.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this); // set click listener

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchButton:
                String terms = searchCompany.getText().toString();
                if (terms.isEmpty()) { // check for empty search field, do not initiate intent unless there are terms
                    Toast.makeText(getContext(), getResources().getString(R.string.inputMissing), Toast.LENGTH_SHORT).show();
                } else {
                    String moddedTerms = terms.replace(" ", "_");
                    Intent intent = new Intent(getActivity(), CompanySearchActivity.class); // create new intent
                    intent.putExtra("searchTerms", moddedTerms); // give the intent the search terms  with key "searchTerms"
                    startActivity(intent);
                }
        }
    }

}