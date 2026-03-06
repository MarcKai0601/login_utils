package org.loginutils.dal.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.loginutils.dal.model.UserRoleDo;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    List<UserRoleDo> findRolesByUserId(Long userId);

    void insertUserRole(UserRoleDo userRoleDo);

}
