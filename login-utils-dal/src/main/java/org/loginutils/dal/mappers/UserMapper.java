package org.loginutils.dal.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.loginutils.dal.model.UserDo;

@Mapper
public interface UserMapper {

    UserDo findList(String username);

    UserDo findByLogin(UserDo user);

    void insert(UserDo user);
}
