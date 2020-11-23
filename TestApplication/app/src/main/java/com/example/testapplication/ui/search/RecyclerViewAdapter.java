package com.example.testapplication.ui.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<CompanyItem> mCompanyList;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    // provide a reference to the type of views that you are using (custom ViewHolder)
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    private void onItemHolderClick(ViewHolder itemHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, itemHolder.itemView, itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }

    public RecyclerViewAdapter(ArrayList<CompanyItem> mCompanyList) {
        this.mCompanyList = mCompanyList;
        Log.e("LISTSIZE", String.valueOf(mCompanyList.size()));
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
        return mCompanyList.size();
    }
}
