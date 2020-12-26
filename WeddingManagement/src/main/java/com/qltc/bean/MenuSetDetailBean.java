/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.bean;

import com.qltc.pojo.MenuSetDetail;
import com.qltc.services.MenuSetDetailService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author phuoc
 */
@ManagedBean
@Named(value = "menuSetDetail")
@SessionScoped
public class MenuSetDetailBean implements Serializable {

    private final static MenuSetDetailService mSDService = new MenuSetDetailService();

    /**
     * Creates a new instance of MenuSetDetail
     */
    public MenuSetDetailBean() {
    }

    public List<MenuSetDetail> getFoodByMenuSet() {
        return mSDService.getMenuSet(2);
    }

}
