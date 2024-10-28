package com.example.todoapp;

import android.os.Bundle;
import android.os.Looper;
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

public class TodoFragment extends Fragment {
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
        todoTasks.addAll(taskStore.getTodoTasks());
        taskAdapter = new TaskAdapter(todoTasks, getContext(), "todo");
        taskStore.setUpdateListener(taskAdapter);
        taskAdapter.setOnTaskCheckListener((position, isChecked) -> {
            if (isChecked) {
                taskStore.deleteTask("todo", todoTasks.get(position), () -> {
                    todoTasks.clear();
                    todoTasks.addAll(taskStore.getTodoTasks());
                    taskAdapter.notifyDataSetChanged();
                });
            }
        });
        recyclerView.setAdapter(taskAdapter);
        return view;
    }

    public void updateView() {
        todoTasks.clear();
        todoTasks.addAll(taskStore.getTodoTasks());
        Log.i("Debug", "len of tasks: " + todoTasks.size());
        taskAdapter.notifyDataSetChanged();
    }
}