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
import com.course.entity.ConsReport;
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
public class ConsBean implements Serializable {

    private ArrayList reportList;

    @PostConstruct
    public void init() {
        reportList = getRepList();
    }

    public ArrayList getReportList() {
        return reportList;
    }

    public void setReportList(ArrayList reportList) {
        this.reportList = reportList;
    }
    
    

   

    public static ArrayList getRepList() {
        ArrayList priorityList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("CALL REPORT_BYCONS");
            while (resultSetObj.next()) {
                ConsReport reqObj = new ConsReport();
                reqObj.setMaterial_name(resultSetObj.getString("material_name"));
                reqObj.setSum(resultSetObj.getInt("SUM(quantity)"));
                reqObj.setMeasure_type(resultSetObj.getString("measure_type"));
                priorityList.add(reqObj);
            }
            System.out.println("Total Records Fetched getFaultList: " + priorityList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return priorityList;
    }
    
}
