/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.bean;

import com.qltc.pojo.Orders;
import com.qltc.pojo.MenuSet;
import com.qltc.pojo.OrderDetail;
import com.qltc.pojo.Product;
import com.qltc.services.OrderService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author phuoc
 */
@ManagedBean
@Named(value = "orderBean")
@SessionScoped
public class OrderBean implements Serializable {

    private final static OrderService orderService = new OrderService();

    private String cusId;
    private String cre;
    private Date start;
    private String pay;
    private boolean status;

    private Set<Product> pro;
    private int table;
    private BigInteger price;
    
    
    private Integer menu;
//    private 

    /**
     * Creates a new instance of OrderBean
     */
    public OrderBean() {
    }

    public List<Orders> getAllOrder() {
        return orderService.getOrders();
    }

    public String addOrder() throws ParseException, Exception {
        Orders o = new Orders();
        o.setCreatedOn(new Date());
        o.setStartOn(getStartDay(this.getStart()));
        o.setPaymentDate(null);
        o.setStatus(this.isStatus());
        
        
        MenuSet menu1 = new MenuSet();
        menu1.setId(menu);
        OrderDetail od = new OrderDetail();
        List<OrderDetail> ods = new ArrayList<>();
        for (Product pr : pro) {
            od.setOrderId(o);
            od.setProductId(pr);
            od.setQuantity(table*10);
            od.setPrice(pr.getPrice());
            ods.add(od);
        }
        if (getStartDay(getStart()) == null) {
            throw new Exception("Vui lòng nhập ngày lớn hơn ngày hiện tại!");
        }
        
        
        if (orderService.addOrder(o, ods) == true) {
            return "index";
        }
        return "index";
    }

    public String getCreateDay() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public Date getStartDay(Date d) throws ParseException {
        java.util.Date d2 = new java.util.Date();
        if (d.compareTo(d2) > 0) {
            return d;
        }
        return null;
    }

    /**
     * @return the cusId
     */
    public String getCusId() {
        return cusId;
    }

    /**
     * @param cusId the cusId to set
     */
    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    /**
     * @return the cre
     */
    public String getCre() {
        return cre;
    }

    /**
     * @param cre the cre to set
     */
    public void setCre(String cre) {
        this.cre = cre;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the pay
     */
    public String getPay() {
        return pay;
    }

    /**
     * @param pay the pay to set
     */
    public void setPay(String pay) {
        this.pay = pay;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the pro
     */
    public Set<Product> getPro() {
        return pro;
    }

    /**
     * @param pro the pro to set
     */
    public void setPro(Set<Product> pro) {
        this.pro = pro;
    }

    /**
     * @return the qty
     */
    public int getTable() {
        return table;
    }

    /**
     * @param qty the qty to set
     */
    public void setTable(int table) {
        this.table = table;
    }

    /**
     * @return the price
     */
    public BigInteger getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigInteger price) {
        this.price = price;
    }

    /**
     * @return the menu
     */
    public Integer getMenu() {
        return menu;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(Integer menu) {
        this.menu = menu;
    }

}
