package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.FinanceEntry;

import java.util.List;

public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder> {
    private List<FinanceEntry> financeEntries;

    public FinanceAdapter(List<FinanceEntry> financeEntries) {
        this.financeEntries = financeEntries;
    }

    @NonNull
    @Override
    public FinanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_finance, parent, false);
        return new FinanceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceViewHolder holder, int position) {
        FinanceEntry financeEntry = financeEntries.get(position);
        holder.title.setText(financeEntry.getTitle());
        holder.amount.setText(String.valueOf(financeEntry.getAmount()));
        holder.category.setText(financeEntry.getCategory());
    }

    @Override
    public int getItemCount() {
        return financeEntries.size();
    }

    static class FinanceViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount, category;

        public FinanceViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            amount = itemView.findViewById(R.id.textViewAmount);
            category = itemView.findViewById(R.id.textViewCategory);
        }
    }
}
