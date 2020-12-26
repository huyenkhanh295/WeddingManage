/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.services;

import com.qltc.pojo.Orders;
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
public class OrderService {

    private final static SessionFactory factory = HibernateUtils.getFACTORY();

    public List<Orders> getOrders() {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Orders> query = builder.createQuery(Orders.class);
            Root<Orders> root = query.from(Orders.class);

            query.select(root);

            return session.createQuery(query).getResultList();
        }
    }

    public boolean addOrder(Orders o, List<OrderDetail> ords) {
        try (Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
                session.save(o);
                for (OrderDetail ord : ords) {
                    session.save(ord);
                }
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();

                System.err.println(ex);
                return false;
            }
        }
    }

}
