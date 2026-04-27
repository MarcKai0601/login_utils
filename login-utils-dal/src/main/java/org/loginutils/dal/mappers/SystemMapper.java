package org.loginutils.dal.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.loginutils.dal.model.SystemDo;

import java.util.List;

@Mapper
public interface SystemMapper {
    int insert(SystemDo record);
    SystemDo selectByPrimaryKey(Long systemId);

    List<SystemDo> selectAll();
}
