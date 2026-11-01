package com.onlinequiz.model;
public class Option {
    private int optionId,questionId; private String optionText; private boolean correct;
    public int getOptionId(){return optionId;} public void setOptionId(int v){optionId=v;}
    public int getQuestionId(){return questionId;} public void setQuestionId(int v){questionId=v;}
    public String getOptionText(){return optionText;} public void setOptionText(String v){optionText=v;}
    public boolean isCorrect(){return correct;} public void setCorrect(boolean v){correct=v;}
}
