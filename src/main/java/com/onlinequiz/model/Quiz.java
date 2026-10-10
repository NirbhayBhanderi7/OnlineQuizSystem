package com.onlinequiz.model;
public class Quiz {
    private int quizId,duration,totalQuestions,createdBy;
    private String title,description;
    public int getQuizId(){return quizId;} public void setQuizId(int v){quizId=v;}
    public int getDuration(){return duration;} public void setDuration(int v){duration=v;}
    public int getTotalQuestions(){return totalQuestions;} public void setTotalQuestions(int v){totalQuestions=v;}
    public int getCreatedBy(){return createdBy;} public void setCreatedBy(int v){createdBy=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
}
