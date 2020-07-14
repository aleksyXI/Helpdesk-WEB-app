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
public class Consumables {
    private int consumables_id;
    private int material_key;
    private String material;
    private int quantity;
    private int request_key;

    public int getConsumables_id() {
        return consumables_id;
    }

    public void setConsumables_id(int consumables_id) {
        this.consumables_id = consumables_id;
    }

    public int getMaterial_key() {
        return material_key;
    }

    public void setMaterial_key(int material_key) {
        this.material_key = material_key;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRequest_key() {
        return request_key;
    }

    public void setRequest_key(int request_key) {
        this.request_key = request_key;
    }
    
}
