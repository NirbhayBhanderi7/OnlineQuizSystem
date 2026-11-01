package com.onlinequiz.model;
public class Leaderboard {
    private int leaderboardId,studentId,quizId,score,rankPosition; private String studentName,quizTitle;
    public int getLeaderboardId(){return leaderboardId;} public void setLeaderboardId(int v){leaderboardId=v;}
    public int getStudentId(){return studentId;} public void setStudentId(int v){studentId=v;}
    public int getQuizId(){return quizId;} public void setQuizId(int v){quizId=v;}
    public int getScore(){return score;} public void setScore(int v){score=v;}
    public int getRankPosition(){return rankPosition;} public void setRankPosition(int v){rankPosition=v;}
    public String getStudentName(){return studentName;} public void setStudentName(String v){studentName=v;}
    public String getQuizTitle(){return quizTitle;} public void setQuizTitle(String v){quizTitle=v;}
}
