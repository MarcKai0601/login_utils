//package org.loginutils.dal.Archive.repository.hibernate;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.loginutils.dal.Archive.repository.entity.UserEntity;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@Profile("hibernate")
//public class UserHibernateRepository {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    public UserEntity findById(Long id){
//        return em.find(UserEntity.class, id);
//    }
//
//    public void save(UserEntity user){
//        em.persist(user);
//    }
//}
