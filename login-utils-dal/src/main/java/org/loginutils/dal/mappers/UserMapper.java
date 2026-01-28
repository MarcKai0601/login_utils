package org.loginutils.dal.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.loginutils.dal.model.UserDo;

@Mapper
public interface UserMapper {

    UserDo findLogin(String username);

    void insert(UserDo user);

    int update(UserDo user);

    int increaseFailedLogin(Long userId);

    int updateLogin(UserDo user);

}
