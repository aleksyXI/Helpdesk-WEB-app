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
import com.course.auth.SessionUtils;
import com.course.entity.User;
import com.course.entity.Comment;
import com.course.entity.Consumables;
import com.course.entity.Perf;
import com.course.entity.Performer;
import com.course.entity.Priority;
import com.course.entity.Requests;
import com.course.entity.Supplies;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author afedo
 */
@Named
@ViewScoped
public class AdminBean implements Serializable {

    private int selected_req;
    
    private int  selected_perf;
    private HttpSession session;
    private User user;
    private ArrayList perfList;
    private ArrayList requestWaitAdminList;
    private ArrayList requestOpenAdminList;
    private ArrayList requestClosedAdminList;
    private ArrayList commentList;
    private ArrayList consumablesList;

    public User getUser() {
        return user;
    }

    public void setSelected_perf(int selected_perf) {
        this.selected_perf = selected_perf;
    }

    public int getSelected_perf() {
        return selected_perf;
    }

    public ArrayList getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList commentList) {
        this.commentList = commentList;
    }

    public ArrayList getConsumablesList() {
        return consumablesList;
    }

    public void setConsumablesList(ArrayList consumablesList) {
        this.consumablesList = consumablesList;
    }
    
    
    
    
    
    
    @PostConstruct
    public void init() {
        session = SessionUtils.getSession();
        user = (User) session.getAttribute("userobject");
        perfList = getPerformerList();
        if (requestWaitAdminList != null && requestClosedAdminList != null && requestOpenAdminList != null) {
            requestWaitAdminList.clear();
            requestClosedAdminList.clear();
            requestOpenAdminList.clear();
        }
        requestWaitAdminList = getAdminWaitRequest();
        requestClosedAdminList = getAdminRequest(5);
        requestOpenAdminList = getAdminRequest(2);
    }

    public ArrayList getPerfList() {
        return perfList;
    }

    public ArrayList getRequestWaitAdminList() {
        return requestWaitAdminList;
    }

    public ArrayList getRequestOpenAdminList() {
        return requestOpenAdminList;
    }

    public ArrayList getRequestClosedAdminList() {
        return requestClosedAdminList;
    }

    public ArrayList getAdminWaitRequest() {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("CALL MY_REQUEST_ADMIN(" + 1 + ")");
            while (resultSetObj.next()) {
                Requests reqObj = new Requests();
                reqObj.setRequest_key(resultSetObj.getInt("request_key"));
                reqObj.setStatus_name(resultSetObj.getString("status_name"));
                reqObj.setFaulty_name(resultSetObj.getString("faulty_name"));
                reqObj.setPriority_name(resultSetObj.getString("priority_name"));
                reqObj.setFIO(resultSetObj.getString("FIO"));
                reqObj.setTelephone_num(resultSetObj.getString("telephone_num"));
                reqObj.setOpening_time(resultSetObj.getTimestamp("opening_time"));
                requestList.add(reqObj);
            }
            System.out.println("Total Records Fetched getAdminRequest: " + requestList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return requestList;
    }

    public ArrayList getAdminRequest(int status) {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("CALL MY_REQUEST_ADMIN(" + status + ")");
            while (resultSetObj.next()) {
                Requests reqObj = new Requests();
                reqObj.setRequest_key(resultSetObj.getInt("request_key"));
                reqObj.setStatus_name(resultSetObj.getString("status_name"));
                reqObj.setFaulty_name(resultSetObj.getString("faulty_name"));
                reqObj.setPerformer_name(resultSetObj.getString("performer_name"));
                reqObj.setPriority_name(resultSetObj.getString("priority_name"));
                reqObj.setFIO(resultSetObj.getString("FIO"));
                reqObj.setTelephone_num(resultSetObj.getString("telephone_num"));
                reqObj.setOpening_time(resultSetObj.getTimestamp("opening_time"));
                reqObj.setClosing_time(resultSetObj.getTimestamp("closing_time"));
                requestList.add(reqObj);
            }
            System.out.println("Total Records Fetched getAdminRequest: " + requestList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return requestList;
    }

    public ArrayList getPerformerList() {
        ArrayList priorityList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("CALL GET_PERF_LIST()");
            while (resultSetObj.next()) {
                Perf reqObj = new Perf();
                reqObj.setPerf_key(resultSetObj.getInt("performer_key"));
                reqObj.setPos_name(resultSetObj.getString("position_name"));
                reqObj.setPerf_name(resultSetObj.getString("performer_name"));
                priorityList.add(reqObj);
            }
            System.out.println("Total Records Fetched getPerfList: " + priorityList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return priorityList;
    }

    public ArrayList getCommentsList(int request_key) {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
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

    public void setPerfReq(int req,int perf) {
        try {
            System.out.println("Performer set: req_key=" + req + "perf_key=" + perf);
            pstmt = getConnection().prepareStatement("CALL SET_PERF_TOREQ(?,?);");
            pstmt.setInt(1, req);
            pstmt.setInt(2, perf);
            pstmt.executeQuery();
            connObj.close();
            requestWaitAdminList = getAdminWaitRequest();
            requestClosedAdminList = getAdminRequest(5);
            requestOpenAdminList = getAdminRequest(2);
            System.out.println("Performer set: req_key=" + req + "perf_key=" + selected_perf);
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public ArrayList getConsumableList() {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("CALL CONSUMABLES_BYREQ(" + selected_req + ")");
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

    public ArrayList getCommentsList() {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("CALL GET_COMM_BYREQ(" + selected_req + ")");
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

    public void overlayPanelInit() {
        if (commentList != null) {
            commentList.clear();
        }
        System.out.println(selected_req + " selected  ");
        commentList = getCommentsList(selected_req);
    }
    
    public void overlayPanelComInit(int req_key) {
        if (commentList != null) {
            commentList.clear();
        }
        selected_req = req_key;
        commentList = getCommentsList();
    }
    public void overlayPanelConsInit(int req_key) {
        if (consumablesList != null) {
            consumablesList.clear();
        }
        selected_req = req_key;
        consumablesList = getConsumableList();
    }

}
