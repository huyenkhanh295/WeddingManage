/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.services;

import com.qltc.pojo.Product;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Vo Pham Huyen Khanh
 */
public class ProductService {

    private final static SessionFactory factory = HibernateUtils.getFACTORY();

    public List<Product> getAllHall(String keyword) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);

            if (!keyword.isEmpty() && keyword != null) {
                String pattern = String.format("%%%s%%", keyword);

                Predicate p1 = builder.equal(root.get("category"), "0301");
                Predicate p2 = builder.like(root.get("name").as(String.class), pattern);
                Predicate p3 = builder.like(root.get("description").as(String.class), pattern);

                query.select(root).where(builder.and(p1, builder.or(p2, p3)));

            } else {
                query.select(root)
                        .where(builder.equal(root.get("category"), "0301"));
            }
            return session.createQuery(query).getResultList();
        }
    }

    public boolean createOrUpdateHall(Product p) {
        try (Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();

                session.saveOrUpdate(p);
                session.getTransaction().commit();

            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }

    public boolean deleteHall(Product p) {
        try (Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
//            Trang thai cua product nay phai la persistence,
//              No da link toi 1 dong nao do trong csdl, con ko la no se khong the xoa dc
                session.delete(p);

                session.getTransaction().commit();

            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }

    public Product getHallById(int hallId) {
        try (Session session = factory.openSession()) {
            return session.get(Product.class, hallId);
        }
    }

    public List<Product> getService() {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);

            Predicate p3 = builder.equal(root.get("type"), "03");
            Predicate p4 = builder.notEqual(builder.substring(root.<String>get("category"), 3, 4), "01");
            query.select(root).where(builder.and(p3, p4));

            return session.createQuery(query).getResultList();
        }
    }

}
