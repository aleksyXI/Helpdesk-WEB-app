/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.course.admin;

import static com.course.DBoperations.DatabaseOperation.connObj;
import static com.course.DBoperations.DatabaseOperation.getConnection;
import static com.course.DBoperations.DatabaseOperation.pstmt;
import static com.course.DBoperations.DatabaseOperation.resultSetObj;
import static com.course.DBoperations.DatabaseOperation.stmtObj;
import com.course.entity.Faulty;
import com.course.entity.Requests;
import com.course.entity.Status;
import com.course.entity.Supplies;
import com.course.entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Aleksy
 */
@Named
@ViewScoped
public class PropBean implements Serializable {

    private ArrayList statusList;
    private ArrayList faultyList;
    private ArrayList suppliesList;
    private int selected_req;
    private HttpSession session;
    private User user;

    @PostConstruct
    public void init() {
        statusList = getStatList();
        faultyList = getFaultList();
    }

    public ArrayList getSuppliesList() {
        return suppliesList;
    }

    public void setSuppliesList(ArrayList suppliesList) {
        this.suppliesList = suppliesList;
    }

    public ArrayList getStatusList() {
        return statusList;
    }

    public void setStatusList(ArrayList statusList) {
        this.statusList = statusList;
    }

    public ArrayList getFaultyList() {
        return faultyList;
    }

    public void setFaultyList(ArrayList faultyList) {
        this.faultyList = faultyList;
    }

    public static ArrayList getFaultList() {
        ArrayList priorityList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("select * from faulty");
            while (resultSetObj.next()) {
                Faulty reqObj = new Faulty();
                reqObj.setFaulty_key(resultSetObj.getInt("faulty_key"));
                reqObj.setFaulty_name(resultSetObj.getString("faulty_name"));
                priorityList.add(reqObj);
            }
            System.out.println("Total Records Fetched getFaultList: " + priorityList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return priorityList;
    }

    public ArrayList getStatList() {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM STATUS");
            while (resultSetObj.next()) {
                Status reqObj = new Status();
                reqObj.setStatus_key(resultSetObj.getInt("status_key"));
                reqObj.setStatus_name(resultSetObj.getString("status_name"));
                requestList.add(reqObj);
            }
            System.out.println("Total Records Fetched getStatList: " + requestList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return requestList;
    }

    public ArrayList getSupplieList() {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("SELECT * FROM supplies_reference");
            while (resultSetObj.next()) {
                Supplies reqObj = new Supplies();
                reqObj.setMaterial_key(resultSetObj.getInt("material_key"));
                reqObj.setMaterial_name(resultSetObj.getString("material_name"));
                reqObj.setMeasure_type(resultSetObj.getString("measure_type"));
                requestList.add(reqObj);
            }
            System.out.println("Total Records Fetched getSuppliesList: " + requestList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return requestList;
    }

    public void editStat(Status stat) {
        try {
            pstmt = getConnection().prepareStatement("update status set status_name=? where status_key=?");
            pstmt.setString(1, stat.getStatus_name());
            pstmt.setInt(2, stat.getStatus_key());
            pstmt.executeUpdate();
            System.out.println("Record added editStat!");
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void addStat(Status stat) {
        try {
            pstmt = getConnection().prepareStatement("INSERT INTO status (status_name) VALUES (?)");
            pstmt.setString(1, stat.getStatus_name());
            pstmt.executeUpdate();
            System.out.println("Stat added!");
            connObj.close();
            statusList = getStatList();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void addFault(Faulty fault) {
        try {
            if (fault.getFaulty_description() != null) {
                pstmt = getConnection().prepareStatement("INSERT INTO faulty (faulty_name,faulty_description) VALUES (?,?)");
                pstmt.setString(1, fault.getFaulty_name());
                pstmt.setString(2, fault.getFaulty_description());
                pstmt.executeUpdate();
                System.out.println("Fault added!");
                connObj.close();
            } else {
                pstmt = getConnection().prepareStatement("INSERT INTO faulty (faulty_name) VALUES (?)");
                pstmt.setString(1, fault.getFaulty_name());
                pstmt.executeUpdate();
                System.out.println("Fault added!");
                connObj.close();
            }
            faultyList = getFaultList();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public void addSuppl(Supplies sup) {
        try {
            pstmt = getConnection().prepareStatement("INSERT INTO supplies_reference (material_name,measure_type) VALUES (?,?)");
            pstmt.setString(1, sup.getMaterial_name());
            pstmt.setString(2, sup.getMeasure_type());
            pstmt.executeUpdate();
            System.out.println("Suppl added!");
            connObj.close();
            overlayPanelSupplInit();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void deleteStatRecordInDB(int key) {
        System.out.println("Deleted status:" + key);
        try {
            pstmt = getConnection().prepareStatement("delete from status where status_key = " + key);
            pstmt.executeUpdate();
            connObj.close();
            overlayPanelStatInit();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void deleteFaultRecordInDB(int key) {
        System.out.println("Deleted faulty:" + key);
        try {
            pstmt = getConnection().prepareStatement("delete from faulty where faulty_key = " + key);
            pstmt.executeUpdate();
            connObj.close();
            overlayPanelFaultInit();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public void deleteSupplRecordInDB(int key) {
        System.out.println("Deleted suppl : " + key);
        try {
            pstmt = getConnection().prepareStatement("delete from supplies_reference where material_key = " + key);
            pstmt.executeUpdate();
            connObj.close();
            overlayPanelSupplInit();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void overlayPanelStatInit() {
        statusList = getStatList();
    }

    public void overlayPanelFaultInit() {
        faultyList = getFaultList();
    }
    
    public void overlayPanelSupplInit() {
        suppliesList = getSupplieList();
    }

    public void onRowStatEdit(RowEditEvent event) {
        Status stat = (Status) event.getObject();
        editStat(stat);
        System.out.println("FFFFFFFF");
        FacesMessage msg = new FacesMessage("Car Edited");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
