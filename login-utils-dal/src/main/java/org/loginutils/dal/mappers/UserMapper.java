package org.loginutils.dal.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.loginutils.dal.model.UserDo;

@Mapper
public interface UserMapper {

    UserDo findLogin(String username);

    void insert(UserDo user);
}
