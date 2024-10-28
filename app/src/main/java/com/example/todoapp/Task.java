package com.example.todoapp;

public class Task {
    private String task;
    private String due;
    private int status;
    private String documentId;

    public Task() {}

    public Task(String task, String due, int status) {
        this.task = task;
        this.due = due;
        this.status = status;
    }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    public String getDue() { return due; }
    public void setDue(String due) { this.due = due; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public void setDocumentId(String id) { documentId = id; }
    public String getDocumentId() { return documentId; }

    @Override
    public String toString() {
        return "Task [ name: " + task + ", document id: " + documentId + " status: " + status;
    }
}
