package com.example.testapplication.ui.search;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    private ArrayList<CompanyItem> mCompanyList;
    private ArrayList<CompanyItem> mCompanyListFiltered;
    private ArrayList<CompanyItem> mFullList;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private boolean clicked = false;

    // provide a reference to the type of views that you are using (custom ViewHolder)
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mCompanyId, mCompanyName, mCompanyRegistrationDate, mCompanyForm; // values the JSON-file has
        public RecyclerViewAdapter mAdapter;

        public ViewHolder (@NonNull View itemView, RecyclerViewAdapter mAdapter) {
            super(itemView);
            this.mAdapter = mAdapter;
            mCompanyId = (TextView) itemView.findViewById(R.id.uiCompanyId);
            mCompanyName = (TextView) itemView.findViewById(R.id.uiCompanyName);
            mCompanyRegistrationDate = (TextView) itemView.findViewById(R.id.uiCompanyRegistrationDate);
            mCompanyForm = (TextView) itemView.findViewById(R.id.uiCompanyType);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClick(this);
            if (!clicked) {
                clicked = true;
                mCompanyId.setVisibility(View.GONE);
                mCompanyRegistrationDate.setVisibility(View.GONE);
                mCompanyForm.setVisibility(View.GONE);
            } else {
                clicked = false;
                mCompanyId.setVisibility(View.VISIBLE);
                mCompanyRegistrationDate.setVisibility(View.VISIBLE);
                mCompanyForm.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString(); // convert search to string
                Log.e("CHARSTRING", charString);
                if (charString.isEmpty()) {
                    mCompanyListFiltered = mFullList; // if no search active, display unfiltered list
                } else {
                    ArrayList<CompanyItem> filteredList = new ArrayList<>(); // make a temporary arrayList for storing
                    for (CompanyItem company : mFullList) { // for each company in mCompanyList
                        if (company.getCompanyName().toLowerCase().contains(charString.toLowerCase())) { // if the company's name contains the search criteria, then...
                            filteredList.add(company);
                        }
                    }
                    mCompanyListFiltered = filteredList; // set filteredList to mCompanyListFiltered
                }
                FilterResults filterResults = new FilterResults(); // initiate filterResults
                filterResults.values = mCompanyListFiltered; // set filterResults-values to be those that are in mCompanyListFiltered
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mCompanyListFiltered = (ArrayList<CompanyItem>) filterResults.values;
                if(mCompanyListFiltered != null) {
                    mCompanyList = mCompanyListFiltered;
                }
                notifyDataSetChanged();
            }
        };
    }

    // public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
      //  this.mOnItemClickListener = onItemClickListener;
    //}


    private void onItemHolderClick(ViewHolder itemHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, itemHolder.itemView, itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }

    public RecyclerViewAdapter(ArrayList<CompanyItem> mCompanyList) {
        this.mCompanyList = mCompanyList;
        this.mFullList = mCompanyList;
    }

    // this creates new views invoked by the layout manager
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        // creates a new view, which defines the UI of the list item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Get elements from your data at this position and replace the contents of the view with that element
        CompanyItem items = mCompanyList.get(position);
        holder.mCompanyId.setText(String.valueOf(items.getId()));
        holder.mCompanyName.setText(String.valueOf(items.getCompanyName()));
        holder.mCompanyRegistrationDate.setText(String.valueOf(items.getDateOfRegistration()));
        holder.mCompanyForm.setText(String.valueOf(items.getCompanyForm()));
    }

    @Override
    public int getItemCount() {
        if (mCompanyList != null) {
            return mCompanyList.size();
        } else {
            return 0;
        }
    }

    public long getItemId(int position) {
        return position;
    }
}
