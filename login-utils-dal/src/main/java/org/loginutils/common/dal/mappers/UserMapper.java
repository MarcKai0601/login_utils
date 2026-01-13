package org.loginutils.common.dal.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.loginutils.common.dal.model.UserDo;

@Mapper
public interface UserMapper {

    UserDo findList(String username);

    void insert(UserDo user);
}
