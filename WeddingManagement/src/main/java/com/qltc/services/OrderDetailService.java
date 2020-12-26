/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.services;

import com.qltc.pojo.OrderDetail;
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
public class OrderDetailService {

    private final static SessionFactory factory = HibernateUtils.getFACTORY();

    public List<OrderDetail> getOrdersDetail() {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<OrderDetail> query = builder.createQuery(OrderDetail.class);
            Root<OrderDetail> root = query.from(OrderDetail.class);

            query.select(root);

            return session.createQuery(query).getResultList();
        }
    }

    public List<OrderDetail> getODById(int id) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<OrderDetail> query = builder.createQuery(OrderDetail.class);
            Root<OrderDetail> root = query.from(OrderDetail.class);
            query.select(root)
                    .where(builder.equal(root.get("orderId"), id));
            return session.createQuery(query).getResultList();

        }
    }


}
