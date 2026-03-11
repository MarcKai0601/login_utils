package org.loginutils.dal.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.loginutils.dal.model.SystemDo;

@Mapper
public interface SystemMapper {
    int insert(SystemDo record);
    SystemDo selectByPrimaryKey(Long systemId);
}
