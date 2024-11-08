package com.example.todoapp;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddIncomeExpenseDialogFragment extends DialogFragment {
    public static final String TAG = "AddIncomeExpenseDialog";

    private EditText titleInput;
    private EditText amountInput;
    private Button addButton;

    public static AddIncomeExpenseDialogFragment newInstance() {
        return new AddIncomeExpenseDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_income_expense_dialog, container, false);

        titleInput = view.findViewById(R.id.titleInput);
        amountInput = view.findViewById(R.id.amountInput);
        addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String amountText = amountInput.getText().toString();

            if (title.isEmpty() || amountText.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                double amount = Double.parseDouble(amountText);
                // You can then pass this data back to the parent fragment or activity
                dismiss();
            }
        });

        return view;
    }
}
