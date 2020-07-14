/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.course.performer;

import com.course.DBoperations.DatabaseOperation;
import static com.course.DBoperations.DatabaseOperation.connObj;
import static com.course.DBoperations.DatabaseOperation.getConnection;
import static com.course.DBoperations.DatabaseOperation.pstmt;
import static com.course.DBoperations.DatabaseOperation.resultSetObj;
import static com.course.DBoperations.DatabaseOperation.stmtObj;
import com.course.auth.SessionUtils;
import com.course.entity.User;
import com.course.entity.Comment;
import com.course.entity.Consumables;
import com.course.entity.Performer;
import com.course.entity.Requests;
import com.course.entity.Supplies;
import com.course.qualifiers.PerformerQualifier;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

/**
 *
 * @author afedo
 */
@Named
@ViewScoped
public class PerformerBean implements Serializable {

    @PerformerQualifier
    private User user;
    private HttpSession session;

    private String tmp;
    private int selected_req;
    private Performer perf;
    private ArrayList myRequestList;
    private ArrayList commentList;
    private ArrayList consumablesList;
    private ArrayList suppliesList;
    private int req_num;

    public ArrayList getSuppliesList() {
        return suppliesList;
    }

    public int getSelected_req() {
        return selected_req;
    }

    public void setSelected_req(int selected_req) {
        System.out.println("Selected req=" + selected_req);
        this.selected_req = selected_req;
    }

    public ArrayList getCommentList() {
        return commentList;
    }

    public ArrayList getConsumablesList() {
        return consumablesList;
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

    public int getReq_num() {
        return req_num;
    }

    public void setReq_num(int req_num) {
        this.req_num = req_num;
    }
    
    

    @PostConstruct
    public void init() {
        session = SessionUtils.getSession();
        perf = (Performer) session.getAttribute("userPerfobject");
        myRequestList = getPerfRequest();
        suppliesList = getSupplieList();
        getReqnum();
    }

    public ArrayList requestList() {
        return myRequestList;
    }

    public ArrayList getPerfRequest() {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("CALL MY_REQUESTS_PERFORMER(" + "\"" + perf.getPerf_key() + "\"" + ")");
            while (resultSetObj.next()) {
                Requests reqObj = new Requests();
                reqObj.setRequest_key(resultSetObj.getInt("request_key"));
                reqObj.setStatus_name(resultSetObj.getString("status_name"));
                reqObj.setFaulty_name(resultSetObj.getString("faulty_name"));
                reqObj.setPriority_name(resultSetObj.getString("priority_name"));
                reqObj.setFIO(resultSetObj.getString("FIO"));
                reqObj.setTelephone_num(resultSetObj.getString("telephone_num"));
                reqObj.setOpening_time(resultSetObj.getTimestamp("opening_time"));
                reqObj.setClosing_time(resultSetObj.getTimestamp("closing_time"));
                requestList.add(reqObj);
            }
            System.out.println("Total Records Fetched PerfRequests: " + requestList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return requestList;
    }
    
     public void getReqnum() {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("CALL GET_MYREQPERF_COUNT(" + "\"" + perf.getPerf_key() + "\"" + ")");
            while (resultSetObj.next()) {
                req_num=resultSetObj.getInt("COUNT(*)");
            }
            System.out.println("Total Records Fetched getReqnum");
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void closeReq(int key) {
        try {
            pstmt = getConnection().prepareStatement("CALL CLOSE_REQ(?);");
            pstmt.setInt(1, key);
            pstmt.executeQuery();
            connObj.close();
            if (myRequestList != null) {
                myRequestList.clear();
                myRequestList = getPerfRequest();
            }
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

    public void addConsumable(Consumables newConsObj) {
        try {
            pstmt = getConnection().prepareStatement("INSERT INTO consumables (material_key,quantity,request_key) VALUES (?,?,?)");
            pstmt.setInt(1, newConsObj.getMaterial_key());
            pstmt.setInt(2, newConsObj.getQuantity());
            pstmt.setInt(3, selected_req);
            pstmt.executeUpdate();
            System.out.println("Record Fetched addConsumable");
            connObj.close();
            consumablesList = getConsumableList();
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
            pstmt.setString(3, perf.getUser());
            pstmt.executeUpdate();
            System.out.println("Comment send!");
            connObj.close();
            commentList = getCommentsList();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
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
