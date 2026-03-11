package org.loginutils.dal.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.loginutils.dal.model.RoleDo;

@Mapper
public interface RoleMapper {
    int insert(RoleDo record);
    RoleDo selectByPrimaryKey(Long roleId);
}
