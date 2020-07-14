/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.course.entity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author afedo
 */
@Named
@RequestScoped
public class Comment {
    private int comment_id;
    private  int request_key;
    private String comment_text;
    private String comment_time;
    private String comment_author;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getRequest_key() {
        return request_key;
    }

    public void setRequest_key(int request_key) {
        this.request_key = request_key;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getComment_author() {
        return comment_author;
    }

    public void setComment_author(String comment_author) {
        this.comment_author = comment_author;
    }
    
}
