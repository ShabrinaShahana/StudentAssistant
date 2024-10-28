package com.example.todoapp;

import static com.example.todoapp.AddNewTask.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> implements TaskStore.TaskUpdateListener {
    private List<Task> taskList;
    private final Context context;
    private OnTaskCheckListener onTaskCheckListener;
    private TaskStore taskStore;
    private String collection;


    @Override
    public void onTaskUpdated() {
        taskList.clear();
        if (collection.equalsIgnoreCase("todo")) taskList.addAll(taskStore.getTodoTasks());
        else taskList.addAll(taskStore.getCompletedTasks());
        notifyDataSetChanged();
    }


    public interface OnTaskCheckListener {
        void onTaskChecked(int position, boolean isChecked);
    }

    public void setOnTaskCheckListener(OnTaskCheckListener listener) {
        this.onTaskCheckListener = listener;
    }

    public TaskAdapter(List<Task> taskList, Context context, String collection) {
        this.taskList = taskList;
        this.context = context;
        this.taskStore = TaskStore.getInstance();
        this.collection = collection;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        if (task != null) {
            holder.taskTitleView.setText(task.getTask());
            holder.dueDateTextView.setText(task.getDue());
            holder.checkBoxView.setOnCheckedChangeListener((buttonView, isChecked) -> {
                onTaskCheckListener.onTaskChecked(holder.getAdapterPosition(), isChecked);
            });
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitleView, dueDateTextView;
        MaterialCheckBox checkBoxView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitleView = itemView.findViewById(R.id.taskTitle);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            checkBoxView = itemView.findViewById(R.id.materialCheckBox);
        }
    }
}
