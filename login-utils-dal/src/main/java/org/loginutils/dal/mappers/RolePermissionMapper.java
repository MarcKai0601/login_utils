package org.loginutils.dal.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.loginutils.dal.model.RolePermissionDo;

@Mapper
public interface RolePermissionMapper {
    int insert(RolePermissionDo record);
    RolePermissionDo selectByPrimaryKey(Long id);
}
