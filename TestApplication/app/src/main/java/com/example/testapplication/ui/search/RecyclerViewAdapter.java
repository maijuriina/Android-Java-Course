package com.example.testapplication.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;

import org.json.JSONObject;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<CompanyItem> mCompanyList;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    // provide a reference to the type of views that you are using (custom ViewHolder)
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mCompanyId, mCompanyName, mCompanyRegistrationDate, mCompanyForm;
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
            mAdapter.mOnItemHolderClick(this);
        }
    }

    private void mOnItemHolderClick(ViewHolder viewHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, viewHolder.itemView, viewHolder.getAdapterPosition(), viewHolder.getItemId());
        }
    }

    public RecyclerViewAdapter(Context context, List<CompanyItem> mCompanyList) {
        this.context = context;
        this.mCompanyList = mCompanyList;
    }

    // this creates new views invoked by the layout manager
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // creates a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get elements from your data at this position and replace the contents of the view with that element
        CompanyItem items = mCompanyList.get(position);
        holder.mCompanyId.setText(items.getId());
        holder.mCompanyName.setText(items.getCompanyName());
        holder.mCompanyRegistrationDate.setText(items.getDateOfRegistration());
        holder.mCompanyForm.setText(items.getCompanyForm());
    }

    @Override
    public int getItemCount() {
        return mCompanyList.size();
    }
}
