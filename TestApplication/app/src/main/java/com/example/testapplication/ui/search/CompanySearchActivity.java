package com.example.testapplication.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapplication.R;
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
    public final ArrayList<CompanyItem> myDataSet = new ArrayList<>();
    RecyclerView mRecyclerView;
    RequestQueue requestQueue; // declare requestQueue to be used by volley
    RecyclerViewAdapter mAdapter;
    public ImageView noResultsIcon;
    public String noResultsText;
    public TextView noResultsTextView;
    private final String url = "http://avoindata.prh.fi/bis/v1.fi.json/bis/v1?totalResults=true&maxResults=30&resultsFrom=0&name=&companyRegistrationFrom=1900-02-28";
    String terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_search);
        foundResults = findViewById(R.id.foundResults); // pair xml elements
        loadingIcon = findViewById(R.id.indeterminateBar);
        receivedTerm = findViewById(R.id.searchTerm);
        noResultsIcon = findViewById(R.id.noResults);
        noResultsText = getResources().getString(R.string.noResults);
        noResultsTextView = findViewById(R.id.noResultsTextView);
        mRecyclerView = findViewById(R.id.companyList); // introduce view that will contain listed cards
        findSearchTerm();
        if (terms != null) {
            buildUrl();
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // layout manager for handling results
            mRecyclerView.setHasFixedSize(true); // makes layout drawing faster
            startTheQueue();
        } else {
            noResultsFound();
        }
    }

    private void findSearchTerm() {
        Bundle extras = getIntent().getExtras(); // find Bundle of search terms
        Log.e("SEARCH TERM", String.valueOf(extras));
        if (extras == null) return;
        terms = extras.getString("searchTerms"); // bind extras to String terms
        if (terms != null) {
        String beautifiedTerms = terms.replace("_", " "); // modify url '_'s to ' 's
        receivedTerm.setText("'" + beautifiedTerms + "'");
        }
    }

    private int findIndex(String wordToFind) { // function for finding ending index of a set word
        int endingIndex; // index place where to begin inserting search term
        Pattern word = Pattern.compile(wordToFind); // make searched wordToFind into a Pattern
        Matcher match = word.matcher(url); // call word.matcher compared to url to find a match
        if (match.find()) {
            endingIndex = match.end(); // where match ends, there is endingIndex
            return endingIndex;
        }
        return 0;
    }

    private String buildUrl() {
        return new StringBuilder(url).insert(findIndex("name="), terms).toString(); // use StringBuilder for url to insert the search term where the ending index of wordToFind is
    }

    private void startTheQueue() {
        if(findIndex("name=&") != 0) { // if meaning when the index is found, so not returning zero
            loadingIcon.setVisibility(View.VISIBLE);
            Log.e("URLI: ", url);
            requestQueue = Volley.newRequestQueue(this); // instantiate the requestQueue
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, buildUrl(), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) { // new JSONObjectRequest for GETting data from YTJ
                    try {
                        JSONArray myArray = response.getJSONArray("results"); // store "results" into JSONArray
                        int total = response.getInt("totalResults"); // get how many in total there were through "totalResults"
                        foundResults.setText(String.valueOf(total)); // set in UI
                        Log.e("JSONHAKU", String.valueOf(total));
                        for (int i = 0; i < myArray.length(); i++) { // until array is looped through
                            JSONObject currentJsonObject = myArray.getJSONObject(i); // find current object by i
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
                    mAdapter = new RecyclerViewAdapter(myDataSet); // specify an adapter to be used by data
                    mAdapter.notifyDataSetChanged(); // notify adapter of changes in the data
                    mRecyclerView.setAdapter(mAdapter); // set adapter
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            noResultsFound();
                            error.printStackTrace();
                        }
                    });

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); // set policies for the search retry
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
                if (mAdapter != null) {
                    mAdapter.getFilter().filter(query); // filter results
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) { // when filter text changes, update filter results continuously on change
                if (mAdapter != null) {
                    mAdapter.getFilter().filter(query);
                }
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

    public void noResultsFound() { // function to show no result UI-elements
        loadingIcon.setVisibility(View.INVISIBLE);
        noResultsTextView.setText(noResultsText);
        noResultsTextView.setVisibility(View.VISIBLE);
        noResultsIcon.setVisibility(View.VISIBLE);
    }

}
