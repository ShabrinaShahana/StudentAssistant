package com.example.todoapp;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private FloatingActionButton mfab;

    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private FirebaseFirestore firebaseFirestore;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        mfab = findViewById(R.id.fab);

        context = this;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Set up RecyclerView
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, context);
        recyclerView.setAdapter(taskAdapter);

        // Load existing tasks
        loadTasksFromFireStore();

        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle FAB click
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    public void loadTasksFromFireStore() {
        firebaseFirestore.collection("tasks")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    taskList.clear(); // Clear the old data
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Task task = documentSnapshot.toObject(Task.class);
                        if (task != null) { // Ensure the task object is not null
                            taskList.add(task);
                        }
                    }
                    taskAdapter.notifyDataSetChanged(); // Notify the adapter of data change
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error getting tasks", e);
                    Toast.makeText(context, "Failed to load tasks", Toast.LENGTH_SHORT).show();
                });
    }
}
