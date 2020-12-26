/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.bean;

import com.qltc.pojo.OrderDetail;
import com.qltc.pojo.Product;
import com.qltc.pojo.Orders;
import com.qltc.services.OrderDetailService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author phuoc
 */
@ManagedBean
@Named(value = "orderDetailBean")
@SessionScoped
public class OrderDetailBean implements Serializable {

    private Orders orders;
    private Product pro;
    private int quantity;
    private BigDecimal price;
    private final static OrderDetailService orderDetailService = new OrderDetailService();

    /**
     * Creates a new instance of OrderDetailBean
     */
    public OrderDetailBean() {

    }

    public List<OrderDetail> getAllOrderDetail() {
        return orderDetailService.getOrdersDetail();
    }

    public List<OrderDetail> getODById() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            String ordId = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("order_id");
            return orderDetailService.getODById(Integer.parseInt(ordId));
        }
        return null;
    }

    /**
     * @return the orders
     */
    public Orders getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    /**
     * @return the pro
     */
    public Product getPro() {
        return pro;
    }

    /**
     * @param pro the pro to set
     */
    public void setPro(Product pro) {
        this.pro = pro;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
