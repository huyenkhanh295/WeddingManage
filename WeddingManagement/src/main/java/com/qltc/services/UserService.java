/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.services;

import com.qltc.pojo.User;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Vo Pham Huyen Khanh
 */
public class UserService {

    private final static SessionFactory factory = HibernateUtils.getFACTORY();

    /*
    This function is used by sign up in user page
    */
    public boolean addUser(User u) {
        try (Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();

                u.setPassword(DigestUtils.md5Hex(u.getPassword()));
                session.save(u);
                
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
        }
        return false;
    }
    
    /*
    This function is used by create and update in admin page
    If the case is update => don't hash the user password
    */
    public boolean createOrUpdateUser(User u) {
        try (Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();

                if(u.getId() == 0){
                    u.setPassword(DigestUtils.md5Hex(u.getPassword()));
                }
                session.saveOrUpdate(u);
                
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
        }
        return false;
    }
    
    public User login(String username, String password){
        password = DigestUtils.md5Hex(password);
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> q = builder.createQuery(User.class);
            Root<User> root = q.from(User.class);
            q.select(root);
            
            Predicate p1 = builder.equal(root.get("username").as(String.class), username);
            Predicate p2 = builder.equal(root.get("password").as(String.class), password);
            
            q.where(builder.and(p1,p2 ));
            
            return session.createQuery(q).getSingleResult();
        }
    }
    
     public List<User> getAllUser(String keyword) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);

            if (!keyword.isEmpty() && keyword != null) {
                String pattern = String.format("%%%s%%", keyword);

                Predicate p1 = builder.like(root.get("username").as(String.class), pattern);
                Predicate p2 = builder.like(root.get("firstname").as(String.class), pattern);
                Predicate p3 = builder.like(root.get("lastname").as(String.class), pattern);

                query.select(root).where(builder.or(p1,p2,p3));

            } else {
                query.select(root);
            }

            return session.createQuery(query).getResultList();
        }
    }
     
    public boolean deleteUser(User u) {
        try (Session session = factory.openSession()) {
            try {
                session.getTransaction().begin();
//            Trang thai cua product nay phai la persistence,
//              No da link toi 1 dong nao do trong csdl, con ko la no se khong the xoa dc
                session.delete(u);
                
                session.getTransaction().commit();
                
            } catch (Exception ex) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }
    
    public User getUserById(int userId){
        try(Session session = factory.openSession()){
            return session.get(User.class, userId);
        }
    }
}
