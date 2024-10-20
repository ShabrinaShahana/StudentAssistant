package com.example.todoapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";

    private TextView setDueDate;
    private EditText taskEditText;
    private Button save_button;
    private FirebaseFirestore firebaseFirestore;
    private Context context;
    private String dueDate = "";

    public static AddNewTask newInstance(){
        return new AddNewTask();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDueDate = view.findViewById(R.id.set_due_tv);
        taskEditText = view.findViewById(R.id.task_editText);
        save_button = view.findViewById(R.id.save_button);

        firebaseFirestore = FirebaseFirestore.getInstance();

        taskEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){
                    save_button.setEnabled(false);
                    save_button.setBackgroundColor(Color.GRAY);
                }
                else{
                    save_button.setEnabled(true);
                    save_button.setBackgroundColor(getResources().getColor(R.color.green_blue));
                    save_button.setTextColor(Color.WHITE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG, "afterTextChanged: ");
            }
        });

        setDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Set Due Date button click
                Calendar calendar = Calendar.getInstance();

                int MONTH = calendar.get(Calendar.MONTH);
                int YEAR = calendar.get(Calendar.YEAR);
                int DAY = calendar.get(Calendar.DATE);

                // You can open a calendar or date picker dialog here
                // For example, using a DatePickerDialog:
                // new DatePickerDialog(requireContext(), ...).show();

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){

                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        setDueDate.setText(date);
                        dueDate = date;

                    }
                }, YEAR, MONTH, DAY);
                datePickerDialog.show();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = taskEditText.getText().toString();

                if (task.isEmpty()){
                    Toast.makeText(context, "Task cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String, Object> taskMap = new HashMap<>();

                    taskMap.put("task", task);
                    taskMap.put("due", dueDate);
                    taskMap.put("status", 0);

                    firebaseFirestore.collection("tasks").add(taskMap).addOnSuccessListener(documentReference -> {

                        Toast.makeText(context, "Task saved successfully", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                    Log.i(TAG, "onSaveButtonClicked: " + firebaseFirestore.collection("tasks").get());
                }
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener) activity).onDialogClose(dialog);
        }
    }
}