package org.loginutils.dal.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.loginutils.dal.model.User;
import org.springframework.context.annotation.Profile;

@Mapper
@Profile("mybatis")
public interface UserMapper {

    User findList(String username);

    void insert(User user);
}
