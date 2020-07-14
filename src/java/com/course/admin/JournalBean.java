/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.course.admin;

import static com.course.DBoperations.DatabaseOperation.connObj;
import static com.course.DBoperations.DatabaseOperation.getConnection;
import static com.course.DBoperations.DatabaseOperation.resultSetObj;
import static com.course.DBoperations.DatabaseOperation.stmtObj;
import com.course.entity.Journal;
import com.course.entity.Requests;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Aleksy
 */
@Named
@ViewScoped
public class JournalBean implements Serializable {

    private ArrayList journ;
    private List<Timestamp> range;

     @PostConstruct
    public void init() {
        
       journ=getJournalRows(0);
    }
    
    public ArrayList getJourn() {
        return journ;
    }

    public void setJourn(ArrayList journ) {
        this.journ = journ;
    }

    public List<Timestamp> getRange() {
        return range;
    }

    public void setRange(List<Timestamp> range) {
        this.range = range;
    }

    public ArrayList getJournalRows(int qual) {
        ArrayList requestList = new ArrayList();
        try {

            stmtObj = getConnection().createStatement();

            if (qual == 1) {
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                String date1 = formatter.format(range.get(0));
                String date2 = formatter.format(range.get(1));
                System.out.println("CALL GET_JOURNAL(" + "\"" + date1 + "\"" + "," + "\"" + date2 + "\"" + ")");
                resultSetObj = stmtObj.executeQuery("CALL GET_JOURNAL(" + "\"" + date1 + "\"" + "," + "\"" + date2 + "\"" + ")");
            } else if (qual == 0) {
                resultSetObj = stmtObj.executeQuery("SELECT * FROM request_journal GROUP BY Date DESC");
            }
            while (resultSetObj.next()) {
                Journal reqObj = new Journal();
                reqObj.setRequest_key(resultSetObj.getInt("request_key"));
                reqObj.setDate(resultSetObj.getTimestamp("Date"));
                reqObj.setCause(resultSetObj.getString("cause"));
                requestList.add(reqObj);
            }
            System.out.println("Total Records Fetched getJournalRows: " + requestList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return requestList;
    }

    public void journalViewInitDates() {
        journ=null;
        journ=getJournalRows(1);
    }

    public void journalViewInitW() {
        journ=getJournalRows(0);
    }
}
