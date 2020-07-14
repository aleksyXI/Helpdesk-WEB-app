/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.course.performer;

import static com.course.DBoperations.DatabaseOperation.connObj;
import static com.course.DBoperations.DatabaseOperation.getConnection;
import static com.course.DBoperations.DatabaseOperation.pstmt;
import static com.course.DBoperations.DatabaseOperation.resultSetObj;
import static com.course.DBoperations.DatabaseOperation.stmtObj;
import com.course.auth.SessionUtils;
import com.course.entity.Comment;
import com.course.entity.Consumables;
import com.course.entity.Performer;
import com.course.entity.Requests;
import com.course.entity.Supplies;
import com.course.entity.User;
import com.course.qualifiers.PerformerQualifier;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aleksy
 */
@Named
@RequestScoped
public class PanelBean {

    @PerformerQualifier
    private User user;
    private HttpSession session;
    private int selected_req;
    private Requests req;

    private Performer perf;

    private ArrayList commentList;
    private ArrayList consumablesList;
    private ArrayList suppliesList;

    @PostConstruct
    public void init() {
        session = SessionUtils.getSession();
        perf = (Performer) session.getAttribute("userPerfobject");
        System.out.println(perf.getPerf_name());

    }

    public ArrayList getSuppliesList() {
        return getSupplieList();
    }

    public int getSelected_req() {
        return selected_req;
    }

    public void setSelected_req(int selected_req) {

        this.selected_req = selected_req;
    }

    public ArrayList getCommentList() {
        return getCommentsList(req.getRequest_key());
    }

    public ArrayList getConsumablesList() {
        return getConsumableList(selected_req);
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Performer getPerf() {
        return perf;
    }

    public void setPerf(Performer perf) {
        this.perf = perf;
    }

    public ArrayList getConsumableList(int request_key) {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            System.out.println(perf.getPerf_key());
            resultSetObj = stmtObj.executeQuery("CALL CONSUMABLES_BYREQ(" + request_key + ")");
            while (resultSetObj.next()) {
                Consumables reqObj = new Consumables();
                reqObj.setMaterial(resultSetObj.getString("material_name"));
                reqObj.setQuantity(resultSetObj.getInt("quantity"));
                requestList.add(reqObj);
            }
            System.out.println("Total Records Fetched getConsumablesList: " + requestList.size());
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
            System.out.println("Performer_key=" + perf.getPerf_key());
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

    public ArrayList getCommentsList(int request_key) {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            System.out.println(perf.getPerf_key());
            resultSetObj = stmtObj.executeQuery("CALL GET_COMM_BYREQ(" + request_key + ")");
            while (resultSetObj.next()) {
                Comment reqObj = new Comment();
                reqObj.setComment_author(resultSetObj.getString("comment_author"));
                reqObj.setComment_text(resultSetObj.getString("comment_text"));
                reqObj.setComment_time(resultSetObj.getString("comment_time"));
                requestList.add(reqObj);
            }
            System.out.println("Total Records Fetched getCommentsList: " + requestList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return requestList;
    }
    
     public void addConsumable(Consumables newConsObj) {
        try {
            pstmt = getConnection().prepareStatement("INSERT INTO consumables (material_key,quantity,request_key) VALUES (?,?,?)");
            pstmt.setInt(1, newConsObj.getMaterial_key());
            pstmt.setInt(2, newConsObj.getQuantity());
            pstmt.setInt(3, selected_req);
            pstmt.executeUpdate();
            System.out.println("Record Fetched addConsumable");
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void sendComment(Comment newComObj) {
        System.out.println("Comment send!");
        try {
            pstmt = getConnection().prepareStatement("INSERT INTO comment (comment_text,request_key,comment_author) VALUES (?,?,?)");
            pstmt.setString(1, newComObj.getComment_text());
            pstmt.setInt(2, selected_req);
            pstmt.setString(3, perf.getPerf_name());
            pstmt.executeUpdate();
            System.out.println("Comment send!");
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

}
