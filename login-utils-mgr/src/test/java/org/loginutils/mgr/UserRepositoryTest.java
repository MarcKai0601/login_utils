package org.loginutils.mgr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.loginutils.dal.config.DalConfig;
import org.loginutils.dal.entity.UserEntity;
import org.loginutils.dal.repository.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void testCreateUser() {
        UserEntity user = new UserEntity();
        user.setUsername("jack");
        user.setPassword("pass123");
        userJpaRepository.save(user);
    }

//    @Test
//    void testFindByUsername() {
//        UserEntity user = userJpaRepository.findByUsername("jack");
//        Assertions.assertTrue(user.isPresent());
//    }
}
