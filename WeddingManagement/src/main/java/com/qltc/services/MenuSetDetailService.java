/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.services;

import com.qltc.pojo.MenuSetDetail;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author phuoc
 */
public class MenuSetDetailService {

    private final static SessionFactory factory = HibernateUtils.getFACTORY();
    public List<MenuSetDetail> getMenuSet(int id) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<MenuSetDetail> query = builder.createQuery(MenuSetDetail.class);
            Root<MenuSetDetail> root = query.from(MenuSetDetail.class);
            query.select(root);
            query = query.where(builder.equal(root.get("menuSetId"), id));
            return session.createQuery(query).getResultList();
        }
    }
}
