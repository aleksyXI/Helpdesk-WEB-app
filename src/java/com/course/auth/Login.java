package com.course.auth;

import com.course.entity.User;
import com.course.DBoperations.DatabaseOperation;
import static com.course.DBoperations.DatabaseOperation.connObj;
import static com.course.DBoperations.DatabaseOperation.getConnection;
import static com.course.DBoperations.DatabaseOperation.resultSetObj;
import static com.course.DBoperations.DatabaseOperation.stmtObj;
import com.course.entity.Performer;
import com.course.qualifiers.PerformerQualifier;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;
    @Inject
    @PerformerQualifier
    private User user;
    
    private Performer perf;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    //validate login
    public String validateUsernamePassword() {
        
        System.out.println("Input user:"+user.getUser());
        boolean valid = DatabaseOperation.validate(user.getUser(), user.getPwd());
        HttpSession session = SessionUtils.getSession();
        if (valid) {            
            user.setRole(DatabaseOperation.role(user.getUser()));
            if("performer".equals(user.getRole()))
            {
                perf=(Performer) user;
                getPerfInfo();
                session.setAttribute("userPerfobject", perf); 
            }
            session.setAttribute("userobject", user); 
            System.out.println(user.getRole());            
            return user.getRole();
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return "login";
        }
    }
    
    public void getPerfInfo() {

        try {
            stmtObj = getConnection().createStatement();
            System.out.println(perf.getUser() + "@@@@");
            resultSetObj = stmtObj.executeQuery("CALL MY_PERFORMER_INFO(" + "\"" + perf.getUser() + "\"" + ")");
            if (resultSetObj != null) {
                resultSetObj.next();
                perf.setPerf_name(resultSetObj.getString("performer_name"));
                perf.setPerf_key(resultSetObj.getInt("performer_key"));
            }
            connObj.close();
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "/login?faces-redirect=true";
    }
}
