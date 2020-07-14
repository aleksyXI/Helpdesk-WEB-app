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
 * @author Aleksy
 */
@Named
@RequestScoped
public class Status {
    private int status_key;
    private String status_name;

    public int getStatus_key() {
        return status_key;
    }

    public void setStatus_key(int status_key) {
        this.status_key = status_key;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
    
}
