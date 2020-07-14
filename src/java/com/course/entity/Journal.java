/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.course.entity;

import java.sql.Date;
import java.sql.Timestamp;


/**
 *
 * @author Aleksy
 */
public class Journal {
    private Timestamp date;
    private int request_key;
    private String cause;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getRequest_key() {
        return request_key;
    }

    public void setRequest_key(int request_key) {
        this.request_key = request_key;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
    
}
