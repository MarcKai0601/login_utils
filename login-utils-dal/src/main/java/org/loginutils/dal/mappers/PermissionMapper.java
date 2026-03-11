package org.loginutils.dal.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.loginutils.dal.model.PermissionDo;

@Mapper
public interface PermissionMapper {
    int insert(PermissionDo record);
    PermissionDo selectByPrimaryKey(Long permissionId);
}
