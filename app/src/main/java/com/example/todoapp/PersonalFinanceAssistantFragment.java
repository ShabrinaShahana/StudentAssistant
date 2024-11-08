package com.example.todoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PersonalFinanceAssistantFragment extends Fragment {

    public PersonalFinanceAssistantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_personal_finance_assistant, container, false);

        // Initialize FloatingActionButton for adding new finance task (like income or expense)
        FloatingActionButton fabAddTask = rootView.findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(view -> {
            // Show a dialog or new fragment for adding a new income/expense
            // You can implement AddIncomeExpenseDialogFragment to handle the addition
            AddIncomeExpenseDialogFragment dialog = new AddIncomeExpenseDialogFragment();
            dialog.show(getFragmentManager(), AddIncomeExpenseDialogFragment.TAG);
        });
        
        

        // Set up RecyclerView or any other UI elements to display finance data
        // e.g., RecyclerView for showing the list of income, expense, or savings
        RecyclerView recyclerViewFinance = rootView.findViewById(R.id.recyclerViewFinance);
        recyclerViewFinance.setLayoutManager(new LinearLayoutManager(getContext()));
        FinanceAdapter financeAdapter = new FinanceAdapter(getSampleFinanceEntries());
        recyclerViewFinance.setAdapter(financeAdapter);
        return rootView;
    }

    private List<FinanceEntry> getSampleFinanceEntries() {
        List<FinanceEntry> entries = new ArrayList<>();
        entries.add(new FinanceEntry("Salary", 5000, "Income"));
        entries.add(new FinanceEntry("Grocery", 200, "Expense"));
        entries.add(new FinanceEntry("Bonus", 1000, "Income"));
        return entries;
    }
}
