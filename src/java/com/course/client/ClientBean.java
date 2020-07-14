/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.course.client;

import static com.course.DBoperations.DatabaseOperation.connObj;
import static com.course.DBoperations.DatabaseOperation.getConnection;
import static com.course.DBoperations.DatabaseOperation.pstmt;
import static com.course.DBoperations.DatabaseOperation.resultSetObj;
import static com.course.DBoperations.DatabaseOperation.stmtObj;
import com.course.auth.SessionUtils;
import com.course.entity.User;
import com.course.entity.Comment;
import com.course.entity.Prior;
import com.course.entity.Flt;
import com.course.entity.Requests;
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
public class ClientBean implements Serializable {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    

    private int selected_req;
    private HttpSession session;
    private ArrayList myRequestList;
    private ArrayList commentList;
    private ArrayList priorityList;
    private ArrayList faultyList;
    private int req_num;
    private Integer selected_prkey;
    private Integer selected_fltkey;

    public Integer getSelected_fltkey() {
        return selected_fltkey;
    }

    public void setSelected_fltkey(Integer selected_fltkey) {
        this.selected_fltkey = selected_fltkey;
    }
    
    

    public Integer getSelected_prkey() {
        return selected_prkey;
    }

    public void setSelected_prkey(Integer selected_prkey) {
        this.selected_prkey = selected_prkey;
    }
    
    

    public ArrayList getFaultyList() {
        return faultyList;
    }

    public void setFaultyList(ArrayList faultyList) {
        this.faultyList = faultyList;
    }

    public ArrayList getMyRequestList() {
        return myRequestList;
    }

    public ArrayList getCommentList() {
        return commentList;
    }

    public ArrayList getPriorityList() {
        return priorityList;
    }

    public int getSelected_req() {
        return selected_req;
    }

    public void setSelected_req(int selected_req) {
        this.selected_req = selected_req;
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
        user = (User) session.getAttribute("userobject");
        myRequestList = getClientRequest();
        priorityList = getPriorList();
        faultyList=getFaultList();
        getReqnum();
    }

    public ArrayList requestList() {
        if (myRequestList != null) {
            myRequestList.clear();
            System.out.println("Request list cleaned!");
        }
        myRequestList = getClientRequest();
        return myRequestList;
    }

    public ArrayList getClientRequest() {
        ArrayList requestList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("CALL MY_REQUEST_CLIENT(" + "\"" + user.getUser() + "\"" + ")");
            while (resultSetObj.next()) {
                Requests reqObj = new Requests();
                reqObj.setRequest_key(resultSetObj.getInt("request_key"));
                reqObj.setStatus_name(resultSetObj.getString("status_name"));
                reqObj.setFaulty_name(resultSetObj.getString("faulty_name"));
                reqObj.setPriority_name(resultSetObj.getString("priority_name"));
                reqObj.setOpening_time(resultSetObj.getTimestamp("opening_time"));
                reqObj.setClosing_time(resultSetObj.getTimestamp("closing_time"));
                requestList.add(reqObj);
            }
            System.out.println("Total Records Fetched getClientRequest: " + requestList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return requestList;
    }
    
    public void getReqnum() {
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("CALL GET_MYREQ_COUNT(" + "\"" + user.getUser() + "\"" + ")");
            while (resultSetObj.next()) {
                req_num=resultSetObj.getInt("COUNT(*)");
            }
            System.out.println("Total Records Fetched getReqnum");
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList getPriorList() {
        ArrayList priorityList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("select * from priority");
            while (resultSetObj.next()) {
                Prior reqObj = new Prior();
                reqObj.setPriority_key(resultSetObj.getInt("priority_key"));
                reqObj.setPriority_name(resultSetObj.getString("priority_name"));
                priorityList.add(reqObj);
            }
            System.out.println("Total Records Fetched getPriorList: " + priorityList.size());
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return priorityList;
    }

    public static ArrayList getFaultList() {
        ArrayList priorityList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("select * from faulty");
            while (resultSetObj.next()) {
                Flt reqObj = new Flt();
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

    public void addReq(Requests newRequestObj) {
        int suc;
        try {
            pstmt = getConnection().prepareStatement("INSERT INTO request (faulty_key,priority_key,FIO,telephone_num,user_name) VALUES (?,?,?,?,?)");
            pstmt.setInt(1, selected_fltkey);
            pstmt.setInt(2, selected_prkey);
            pstmt.setString(3, newRequestObj.getFIO());
            pstmt.setString(4, newRequestObj.getTelephone_num());
            pstmt.setString(5, user.getUser());
            suc = pstmt.executeUpdate();
            System.out.println("Request added for" + user.getUser());
            connObj.close();
            requestList();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
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

    public void sendComment(Comment newComObj) {
        try {
            pstmt = getConnection().prepareStatement("INSERT INTO comment (comment_text,request_key,comment_author) VALUES (?,?,?)");
            pstmt.setString(1, newComObj.getComment_text());
            pstmt.setInt(2, selected_req);
            pstmt.setString(3, user.getUser());
            pstmt.executeUpdate();
            connObj.close();
            commentList = getCommentsList();
            System.out.println("Comment send for=" + selected_req + " name=" + user.getUser());
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
}
