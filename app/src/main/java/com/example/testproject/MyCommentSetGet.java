package com.example.testproject;

public class MyCommentSetGet {

    private String comment;
    private String dishName;

    public MyCommentSetGet(){

    }

    public MyCommentSetGet(String comment, String dishName){
        this.comment = comment;
        this.dishName = dishName;
    }

    public String getComment (){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getName (){
        return dishName;
    }

    public void setName(String dishName){
        this.dishName = dishName;
    }
}
