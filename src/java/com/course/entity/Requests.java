/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.course.entity;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author afedo
 */
@Named
@RequestScoped
public class Requests{
    private Integer request_key;
    private int status_key;
    private String status_name;
    private int priority_key;
    private String priority_name;
    private Integer performer_key;
    private String performer_name;
    private int faulty_key;
    private String faulty_name;
    private String FIO;
    private String telephone_num;
    private Date opening_time;
    private Date closing_time;

    public Integer getRequest_key() {
        return request_key;
    }

    public void setRequest_key(Integer request_key) {
        this.request_key = request_key;
    }

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

    public Integer getPerformer_key() {
        return performer_key;
    }

    public void setPerformer_key(Integer performer_key) {
        this.performer_key = performer_key;
    }

    public String getPerformer_name() {
        return performer_name;
    }

    public void setPerformer_name(String performer_name) {
        this.performer_name = performer_name;
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

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getTelephone_num() {
        return telephone_num;
    }

    public void setTelephone_num(String telephone_num) {
        this.telephone_num = telephone_num;
    }

    public Date getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(Date opening_time) {
        this.opening_time = opening_time;
    }

    public Date getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(Date closing_time) {
        this.closing_time = closing_time;
    }
    
    
}
