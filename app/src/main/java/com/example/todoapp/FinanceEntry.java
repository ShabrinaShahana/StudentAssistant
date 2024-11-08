package com.example.todoapp;

public class FinanceEntry {
    private String title;
    private double amount;
    private String category; // "Income" or "Expense"

    public FinanceEntry(String title, double amount, String category) {
        this.title = title;
        this.amount = amount;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }
}
