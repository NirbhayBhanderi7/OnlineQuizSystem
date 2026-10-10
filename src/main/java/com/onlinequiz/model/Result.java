package com.onlinequiz.model;
public class Result {
    private int resultId,studentId,quizId,score,totalMarks,correctAnswers,wrongAnswers;
    private String studentName,quizTitle,attemptedAt;
    public int getResultId(){return resultId;} public void setResultId(int v){resultId=v;}
    public int getStudentId(){return studentId;} public void setStudentId(int v){studentId=v;}
    public int getQuizId(){return quizId;} public void setQuizId(int v){quizId=v;}
    public int getScore(){return score;} public void setScore(int v){score=v;}
    public int getTotalMarks(){return totalMarks;} public void setTotalMarks(int v){totalMarks=v;}
    public int getCorrectAnswers(){return correctAnswers;} public void setCorrectAnswers(int v){correctAnswers=v;}
    public int getWrongAnswers(){return wrongAnswers;} public void setWrongAnswers(int v){wrongAnswers=v;}
    public String getStudentName(){return studentName;} public void setStudentName(String v){studentName=v;}
    public String getQuizTitle(){return quizTitle;} public void setQuizTitle(String v){quizTitle=v;}
    public String getAttemptedAt(){return attemptedAt;} public void setAttemptedAt(String v){attemptedAt=v;}
}
