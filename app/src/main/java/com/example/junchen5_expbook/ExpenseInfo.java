package com.example.junchen5_expbook;

public class ExpenseInfo {
    private String name;

    private String monthStarted;

    private Double monthlyCharged;

    private String comment;

    public ExpenseInfo(String name, String monthStarted, Double monthlyCharged, String comment) {
        this.name = name;
        this.monthStarted = monthStarted;
        this.monthlyCharged = monthlyCharged;
        this.comment = comment;
    }


    // Initializing the getter and setter method
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonthStarted() {
        return monthStarted;
    }

    public void setMonthStarted(String monthStarted) {
        this.monthStarted = monthStarted;
    }

    public Double getMonthlyCharged() {
        return monthlyCharged;
    }

    public void setMonthlyCharged(Double monthlyCharged) {
        this.monthlyCharged = monthlyCharged;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
