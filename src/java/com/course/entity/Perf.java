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
public class Perf {
    private int perf_key;
    private String perf_name;
    private String pos_name;

    public String getPos_name() {
        return pos_name;
    }

    public void setPos_name(String pos_name) {
        this.pos_name = pos_name;
    }
    
    

    public int getPerf_key() {
        return perf_key;
    }

    public void setPerf_key(int perf_key) {
        this.perf_key = perf_key;
    }

    public String getPerf_name() {
        return perf_name;
    }

    public void setPerf_name(String perf_name) {
        this.perf_name = perf_name;
    }
    
    
}
