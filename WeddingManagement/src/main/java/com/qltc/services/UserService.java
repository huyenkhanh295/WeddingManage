/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.services;

import com.qltc.pojo.User;
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
}
