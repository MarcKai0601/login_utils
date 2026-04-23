//package org.loginutils.mgr;
//
//import org.junit.jupiter.api.Test;
//import org.loginutils.dal.mappers.UserMapper;
//import org.loginutils.dal.model.UserDo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
////@SpringBootTest(classes = DalConfig.class)
//@SpringBootTest
//public class UserRepositoryTest {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    public void testCreateUser() {
//        UserDo user = new UserDo();
//        user.setUsername("test02");
//        user.setPassword("pass123");
//        user.setStatus(1);
//        userMapper.insert(user);
//        System.out.println("OK");
//    }
//
//    @Test
//    void testFindByUsername() {
////        UserDo user = userMapper.findList("kai");
////        System.out.println(user.getUsername());
//    }
//}
