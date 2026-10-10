package com.onlinequiz.model;
import java.util.List;
public class Question {
    private int questionId,quizId,marks; private String questionText; private List<Option> options;
    public int getQuestionId(){return questionId;} public void setQuestionId(int v){questionId=v;}
    public int getQuizId(){return quizId;} public void setQuizId(int v){quizId=v;}
    public int getMarks(){return marks;} public void setMarks(int v){marks=v;}
    public String getQuestionText(){return questionText;} public void setQuestionText(String v){questionText=v;}
    public List<Option> getOptions(){return options;} public void setOptions(List<Option> v){options=v;}
}
