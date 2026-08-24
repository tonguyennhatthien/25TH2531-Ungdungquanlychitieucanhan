package com.thien.quanlychitieu;

public class Expense {
    private String id, title, category, note, date;
    private double amount;

    public Expense() {}

    public Expense(String title, double amount, String category, String note, String date) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
