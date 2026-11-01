package com.onlinequiz.model;

public class Quiz {
    private int quizId, duration, totalQuestions, createdBy, timePerQuestionMinutes;
    private String title, description;

    public int getQuizId() { return quizId; }
    public void setQuizId(int value) { quizId = value; }
    public int getDuration() { return duration; }
    public void setDuration(int value) { duration = value; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int value) { totalQuestions = value; }
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int value) { createdBy = value; }
    public int getTimePerQuestionMinutes() { return timePerQuestionMinutes; }
    public void setTimePerQuestionMinutes(int value) { timePerQuestionMinutes = value; }
    public String getTitle() { return title; }
    public void setTitle(String value) { title = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { description = value; }
}
