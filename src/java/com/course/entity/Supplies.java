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
public class Supplies {
    private int material_key;
    private String material_name;
    private String measure_type;

    public int getMaterial_key() {
        return material_key;
    }

    public void setMaterial_key(int material_key) {
        this.material_key = material_key;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public String getMeasure_type() {
        return measure_type;
    }

    public void setMeasure_type(String measure_type) {
        this.measure_type = measure_type;
    }
}
