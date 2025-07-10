package org.loginutils.mgr.service;

import org.loginutils.dal.entity.UserEntity;
import org.loginutils.dal.model.User;
import org.loginutils.dal.repository.jpa.UserJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;

    public UserService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public void createUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userJpaRepository.save(userEntity);
    }
}
