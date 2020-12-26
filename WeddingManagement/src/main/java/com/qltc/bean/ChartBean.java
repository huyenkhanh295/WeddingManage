/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Vo Pham Huyen Khanh
 */
@ManagedBean
@Named(value = "chartBean")
@SessionScoped
public class ChartBean implements Serializable {

    private String[] countries = { "'Germany'", "'USA'", "'Brazil'", "'Canada'", "'France'", "'Russia'" };
    private String[] numbers = { "700", "300", "400", "500", "600", "800" };

    
    /**
     * Creates a new instance of ChartBean
     */
    public ChartBean() {
        super();
        System.out.println("ChartBeean..");
    }
    
    @PostConstruct
    public void init() {
        System.out.println("ChartBeean init..");
    }

    public String getCountriesString() {

        // ['Germany', 'USA', 'Brazil', 'Canada', 'France', 'Russia']
        String deepToString = Arrays.deepToString(countries);
        System.out.println(deepToString);
        return deepToString;
    }

    public String getNumbersString() {
        // [700, 300, 400, 500, 600, 800]
        String deepToString = Arrays.deepToString(numbers);
        System.out.println(deepToString);
        return deepToString;
    }
    
}
