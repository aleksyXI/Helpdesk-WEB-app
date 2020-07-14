/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.course.entity;

/**
 *
 * @author Aleksy
 */
public class Flt {
    private int faulty_key;
    private String faulty_name;
    private String faulty_description;

    public String getFaulty_description() {
        return faulty_description;
    }

    public void setFaulty_description(String faulty_description) {
        this.faulty_description = faulty_description;
    }
    

    public int getFaulty_key() {
        return faulty_key;
    }

    public void setFaulty_key(int faulty_key) {
        this.faulty_key = faulty_key;
    }

    public String getFaulty_name() {
        return faulty_name;
    }

    public void setFaulty_name(String faulty_name) {
        this.faulty_name = faulty_name;
    }
    
    
}
