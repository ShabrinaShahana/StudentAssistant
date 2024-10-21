package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private FloatingActionButton mfab;
    private List<Task> taskList;

    private TaskAdapter taskAdapter;
    private Context context;

    private TaskStore taskStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        taskStore = TaskStore.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        mfab = findViewById(R.id.fab);

        context = this;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        Log.i(TAG, "updateView");
        taskList = new ArrayList<>();
        taskList.addAll(taskStore.getTasks("todo"));
        taskAdapter = new TaskAdapter(taskList, context);
        taskStore.setUpdateListener(taskAdapter);
        taskAdapter.setOnTaskCheckListener((position, isChecked) -> {
            Log.i("Debug", "onTaskChecked => position: " + position + " isChecked: " + isChecked);
            if (isChecked) {
                taskStore.deleteTask("todo", taskList.get(position), () -> {
                    taskList.clear();
                    taskList.addAll(taskStore.getTasks("todo"));
                    taskAdapter.notifyDataSetChanged();
                });
            }
        });
        recyclerView.setAdapter(taskAdapter);

        mfab.setOnClickListener(view -> AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG));
    }

    public void updateView() {
        Log.i(TAG, "updateView");
        taskList.clear();
        taskList.addAll(taskStore.getTasks("todo"));
        taskAdapter.notifyDataSetChanged();
    }
}
