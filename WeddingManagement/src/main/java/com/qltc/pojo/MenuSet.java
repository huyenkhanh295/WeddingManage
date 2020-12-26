/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.pojo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author phuoc
 */
@Entity
@Table(name = "menu_set")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MenuSet.findAll", query = "SELECT m FROM MenuSet m"),
    @NamedQuery(name = "MenuSet.findById", query = "SELECT m FROM MenuSet m WHERE m.id = :id"),
    @NamedQuery(name = "MenuSet.findByName", query = "SELECT m FROM MenuSet m WHERE m.name = :name"),
    @NamedQuery(name = "MenuSet.findByStatus", query = "SELECT m FROM MenuSet m WHERE m.status = :status"),
    @NamedQuery(name = "MenuSet.findByTotalAmount", query = "SELECT m FROM MenuSet m WHERE m.totalAmount = :totalAmount")})
public class MenuSet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "total_amount")
    private BigInteger totalAmount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menuSetId")
    private Collection<MenuSetDetail> menuSetDetailCollection;

    public MenuSet() {
    }

    public MenuSet(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BigInteger getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigInteger totalAmount) {
        this.totalAmount = totalAmount;
    }

    @XmlTransient
    public Collection<MenuSetDetail> getMenuSetDetailCollection() {
        return menuSetDetailCollection;
    }

    public void setMenuSetDetailCollection(Collection<MenuSetDetail> menuSetDetailCollection) {
        this.menuSetDetailCollection = menuSetDetailCollection;
    }

  
    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

    @Override
    public boolean equals(Object object) {
        MenuSet menu = (MenuSet) object;

        return this.id == menu.id;
    }
     
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 91 * hash + this.id;
        return hash;
    }

    
}
