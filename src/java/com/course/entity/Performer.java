/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.course.entity;

import com.course.entity.User;
import com.course.qualifiers.PerformerQualifier;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author afedo
 */
@Named
@SessionScoped
@PerformerQualifier
public class Performer extends User implements Serializable{
    private int perf_key;
    private String perf_name;
    private int pos_key;
    private String pos_name;

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

    public int getPos_key() {
        return pos_key;
    }

    public void setPos_key(int pos_key) {
        this.pos_key = pos_key;
    }

    public String getPos_name() {
        return pos_name;
    }

    public void setPos_name(String pos_name) {
        this.pos_name = pos_name;
    }
    
    
}
