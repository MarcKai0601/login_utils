package org.loginutils.mgr;

import org.junit.jupiter.api.Test;
import org.loginutils.dal.config.DalConfig;
import org.loginutils.dal.mappers.UserMapper;
import org.loginutils.dal.model.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest(classes = DalConfig.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("test02");
        user.setPassword("pass123");
        user.setStatus(1);
        userMapper.insert(user);
        System.out.println("OK");
    }

    @Test
    void testFindByUsername() {
        User user = userMapper.findList("kai");
        System.out.println(user.getUsername());
    }
}
