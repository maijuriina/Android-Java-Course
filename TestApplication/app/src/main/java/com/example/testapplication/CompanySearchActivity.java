package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapplication.ui.search.CompanyItem;
import com.example.testapplication.ui.search.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompanySearchActivity extends AppCompatActivity {
    private TextView receivedTerm;
    private TextView foundResults;
    private ProgressBar loadingIcon;
    public ArrayList<CompanyItem> myDataSet = new ArrayList<>();
    RecyclerView mRecyclerView;
    RequestQueue requestQueue; // declare requestQueue to be used by volley
    String url = "http://avoindata.prh.fi/bis/v1.fi.json/bis/v1?totalResults=true&maxResults=20&resultsFrom=0&name=&companyRegistrationFrom=1900-02-28";
    String terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_search);
        foundResults = findViewById(R.id.foundResults);
        loadingIcon = findViewById(R.id.indeterminateBar);
        receivedTerm = findViewById(R.id.searchTerm);
        findSearchTerm();
        buildUrl();
        mRecyclerView = findViewById(R.id.companyList); // introduce view that will contain listed cards
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        startTheQueue();
    }

    private void findSearchTerm() {
        Bundle extras = getIntent().getExtras();
        Log.e("OSAAAAAAAAAA", String.valueOf(extras));
        if (extras == null) return;
        terms = extras.getString("searchTerms");
        if (terms != null) {
            Log.e("OSAAAAAAAAAA", terms);
            receivedTerm.setText("'" + terms + "'");
        }
    }

    private int findIndex() {
        int endingIndex;
        String wordToFind = "name=";
        Pattern word = Pattern.compile(wordToFind);
        Matcher match = word.matcher(url);
        while (match.find()) {
            endingIndex = match.end();
            Log.e("INDEXIIIIIIII", String.valueOf(endingIndex));
            return endingIndex;
        }
        return 0;
    }

    private String buildUrl() {
        return new StringBuilder(url).insert(findIndex(), terms).toString();
    }

    private void startTheQueue() {
        loadingIcon.setVisibility(View.VISIBLE);
        requestQueue = Volley.newRequestQueue(this); // instantiate the requestQueue
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, buildUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray myArray = response.getJSONArray("results");
                    int total = response.getInt("totalResults");
                    foundResults.setText(String.valueOf(total));
                    Log.e("JSONHAKU", String.valueOf(total));
                    for (int i = 0; i < myArray.length(); i++) {
                        JSONObject currentJsonObject = myArray.getJSONObject(i);
                        String businessId = currentJsonObject.getString("businessId"); // get the data in question from JSON with its name tag and bind into variable
                        String name = currentJsonObject.getString("name");
                        String registrationDate = currentJsonObject.getString("registrationDate");
                        String companyForm = currentJsonObject.getString("companyForm");
                        myDataSet.add(new CompanyItem(businessId, name, registrationDate, companyForm)); // add to myDataSet
                        Log.e("MYDATASET", String.valueOf(myDataSet));
                        Log.i("JSONHAKU", businessId);
                        Log.i("JSONHAKU", name);
                        Log.i("JSONHAKU", registrationDate);
                        Log.i("JSONHAKU", companyForm);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadingIcon.setVisibility(View.INVISIBLE);
                RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(myDataSet); // specify an adapter to be used by data and set it
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest); // Start the queue
    }

}
