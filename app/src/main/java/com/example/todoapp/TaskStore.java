package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TaskStore {
    private static final String TAG = "TaskStore";

    private static FirebaseFirestore firebaseFirestore;
    private static List<Task> todoTasks;
    private static List<Task> completedTasks;
    private static TaskStore instance;
    private Context context;
    private TaskUpdateListener listener;

    private TaskStore() {
        todoTasks = new ArrayList<>();
        completedTasks = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public static TaskStore getInstance() {
        if (instance == null) {
            instance = new TaskStore();
            todoTasks = readTasks("todo");
            completedTasks = readTasks("completed");
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private static List<Task> readTasks(String collection) {
        List<Task> taskList = new ArrayList<>();

        firebaseFirestore.collection(collection)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        taskList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Task taskItem = document.toObject(Task.class);
                            taskItem.setDocumentId(document.getId());
                            taskList.add(taskItem);
                        }
                    }
                });
        return taskList;
    }

    public void addTask(String collection, Task task, TaskUpdateListener taskUpdateListener) {
        firebaseFirestore.collection(collection)
                .add(task)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Task added with ID: " + documentReference.getId());
                    task.setDocumentId(documentReference.getId());
                    if (collection.equalsIgnoreCase("todo")) {
                        todoTasks.add(task);
                    } else {
                        completedTasks.add(task);
                    }
                    taskUpdateListener.onTaskUpdated();
                });
        listener.onTaskUpdated();
    }

    public void setUpdateListener(TaskUpdateListener listener) {
        this.listener = listener;
    }

    public void deleteTask(String collection, Task task, TaskUpdateListener taskUpdateListener) {
        firebaseFirestore.collection(collection)
                .document(task.getDocumentId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Task successfully deleted!");
                    if (collection.equalsIgnoreCase("todo")) {
                        todoTasks.removeIf(t -> t.getDocumentId().equals(task.getDocumentId()));
                    } else {
                        completedTasks.removeIf(t -> t.getDocumentId().equals(task.getDocumentId()));
                    }
                    if (taskUpdateListener != null) taskUpdateListener.onTaskUpdated();
                })
                .addOnFailureListener(e -> {
                    Log.w("TaskStore", "Error deleting task", e);
                });
        if (listener != null) listener.onTaskUpdated();
    }

    public List<Task> getTasks(String collection) {
        if (collection.equalsIgnoreCase("todo")) {
            if (todoTasks.isEmpty()) {
                return readTasks("todo");
            }
            return todoTasks;
        } else {
            if (completedTasks.isEmpty()) {
                return readTasks("completed");
            }
            return completedTasks;
        }
    }

    public interface TaskUpdateListener {
        void onTaskUpdated();
    }
}
