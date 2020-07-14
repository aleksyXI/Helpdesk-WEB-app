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
public class Priority {
    private int priority_key;
    private String priority_name;

    public int getPriority_key() {
        return priority_key;
    }

    public void setPriority_key(int priority_key) {
        this.priority_key = priority_key;
    }

    public String getPriority_name() {
        return priority_name;
    }

    public void setPriority_name(String priority_name) {
        this.priority_name = priority_name;
    }
    
    
}
