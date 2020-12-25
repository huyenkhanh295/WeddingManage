/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.bean;

import com.qltc.pojo.User;
import com.qltc.services.UserService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.Transient;

/**
 *
 * @author Vo Pham Huyen Khanh
 */
@ManagedBean
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    private String lastname;
    private String firstname;
    private String email;
    private String username;
    private String password;
    @Transient
    private String confirmPassword;
    private static final UserService userService = new UserService();

    public String register() {
        if (this.password.equals(this.confirmPassword)) {
            User u = new User();
            u.setLastname(this.lastname);
            u.setFirstname(this.firstname);
            u.setEmail(this.email);
            u.setUsername(this.username);
            u.setPassword(this.password);
            u.setUserRole("USER");

            if (userService.addUser(u) == true) {
                return "sign-in?faces-redirect=true";
            }
        }

        return "sign-up";
    }

    public String login() {
        try {
            User u = userService.login(username, password);
            if (u != null && u.getId() > 1) {
                FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().put("user", u);

                String admin = u.getUserRole();

                if ("ADMIN".equals(admin)) {
                    return "index-admin?faces-redirect=true";
                } else {
                    return "index?faces-redirect=true";
                }
            }
        } catch(Exception ex){
            return "sign-in";
        }
        return "sign-in";
    }

    public String checkLogin() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user") != null) {
            return "index?faces-redirect=true";
        }
        return null;
    }

    public String checkNotLogin() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user") == null) {
            return "sign-in?faces-redirect=true";
        }
        return null;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().remove("user");
        return "sign-in?faces-redirect=true";
    }

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
