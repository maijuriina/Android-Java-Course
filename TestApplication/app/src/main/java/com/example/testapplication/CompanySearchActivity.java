package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
// import android.widget.SearchView;
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
    RecyclerViewAdapter mAdapter;
    public ImageView noResultsIcon;
    public String noResultsText;
    public TextView noResultsTextView;
    private String url = "http://avoindata.prh.fi/bis/v1.fi.json/bis/v1?totalResults=true&maxResults=30&resultsFrom=0&name=&companyRegistrationFrom=1900-02-28";
    String terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_search);
        foundResults = findViewById(R.id.foundResults);
        loadingIcon = findViewById(R.id.indeterminateBar);
        receivedTerm = findViewById(R.id.searchTerm);
        noResultsIcon = findViewById(R.id.noResults);
        noResultsText = getResources().getString(R.string.noResults);
        noResultsTextView = findViewById(R.id.noResultsTextView);
        findSearchTerm();
        buildUrl();
        mRecyclerView = findViewById(R.id.companyList); // introduce view that will contain listed cards
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        startTheQueue();
    }

    private void findSearchTerm() {
        Bundle extras = getIntent().getExtras();
        Log.e("SEARCH TERM", String.valueOf(extras));
        if (extras == null) return;
        terms = extras.getString("searchTerms");
        if (terms != null) {
            Log.e("SEARCH TERM", terms);
            receivedTerm.setText("'" + terms + "'");
        }
    }

    private int findIndex(String wordToFind) {
        int endingIndex;
        // wordToFind = "name=";
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
        return new StringBuilder(url).insert(findIndex("name="), terms).toString();
    }

    private void startTheQueue() {
        if(findIndex("name=&") != 0) {  // löytää indeksin
            loadingIcon.setVisibility(View.VISIBLE);
            Log.e("URLI: ", url);
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
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadingIcon.setVisibility(View.INVISIBLE);
                    mAdapter = new RecyclerViewAdapter(myDataSet); // specify an adapter to be used by data and set it
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loadingIcon.setVisibility(View.INVISIBLE);
                            noResultsTextView.setText(noResultsText);
                            noResultsTextView.setVisibility(View.VISIBLE);
                            noResultsIcon.setVisibility(View.VISIBLE);
                            error.printStackTrace();
                        }
                    });

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest); // Start the queue
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // to inflate the search bar xml when moving to adapter view
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())); // SearchableInfo object is created in searchable xml --> associate --> starts activity with ACTION_SEARCH intent when query is submitted

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
