package com.example.todoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CompletedFragment extends Fragment {

    private List<Task> todoTasks;
    private TaskStore taskStore;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_todo, container, false);
        taskStore = TaskStore.getInstance();
        recyclerView = view.findViewById(R.id.todoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        todoTasks = new ArrayList<>();
        todoTasks.addAll(taskStore.getCompletedTasks());
        taskAdapter = new TaskAdapter(todoTasks, getContext(), "completed");
        taskStore.setUpdateListener(taskAdapter);
        recyclerView.setAdapter(taskAdapter);
        return view;
    }

    public void updateView() {
        todoTasks.clear();
        todoTasks.addAll(taskStore.getCompletedTasks());
        taskAdapter.notifyDataSetChanged();
    }
}
