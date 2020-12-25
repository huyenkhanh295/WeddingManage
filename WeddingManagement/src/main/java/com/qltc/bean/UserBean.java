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
import java.util.List;
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
private int userId;
    private String lastname;
    private String firstname;
    private String email;
    private String username;
    private String password;
    private String phone;
    private String address;
    private String userRole;
    @Transient
    private String confirmPassword;
    private static final UserService userService = new UserService();

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            String user_Id = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestParameterMap().get("user_id");
            if(user_Id != null && !user_Id.isEmpty()){
                User u = userService.getUserById(Integer.parseInt(user_Id));
                this.userId = u.getId();
                this.firstname = u.getFirstname();
                this.lastname = u.getLastname();
                this.email = u.getEmail();
                this.username = u.getUsername();
                this.password = u.getPassword();
                this.phone = u.getPhone();
                this.address = u.getAddress();
                this.userRole = u.getUserRole();
            }
        }
    }
    
    public String register() {
        if (this.password.equals(this.confirmPassword)) {
            User u = new User();
            u.setLastname(this.lastname);
            u.setFirstname(this.firstname);
            u.setEmail(this.email);
            u.setUsername(this.username);
            u.setPassword(this.password);
            u.setUserRole("USER");

            if (getUserService().addUser(u) == true) {
                return "sign-in?faces-redirect=true";
            }
        }

        return "sign-up";
    }

    public String login() {
        try {
            User u = getUserService().login(username, password);
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
    
    public List<User> getAllUser() {
        List<User> users = getUserService().getAllUser();
        return users;
    }
    
    //create update chung mot ham
    public String createOrUpdateUser() {
        String userId = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestParameterMap().get("user_id");
        User u;
        if(this.userId > 0) 
//            trang thai persistence: da link toi 1 product duoi db
            u = userService.getUserById(this.userId);
        else
            u = new User(); //transient: trang thai moi hoan toan
        
        u.setLastname(this.lastname);
        u.setFirstname(this.firstname);
        u.setEmail(this.email);
        u.setUsername(this.username);
        
        /*
        khong cho phep admin update password cua user
        => set lai password cu cua user
        */
        if(this.userId > 0){
            u.setPassword(u.getPassword());
        }else{
            u.setPassword(this.password);
        }
        u.setPhone(this.phone);
        u.setAddress(this.address);
        u.setUserRole(this.userRole);
       
            if (userService.createOrUpdateUser(u) == true) {
                return "manage-user?faces-redirect=true";
            }
        
        return "user";
    }
    
    public String deleteUser(User u) throws Exception {
        if (userService.deleteUser(u)) {
            return "successful";
        }
        throw new Exception("Something wrong!!!");
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

    /**
     * @return the userService
     */
    public static UserService getUserService() {
        return userService;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

}
