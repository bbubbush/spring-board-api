package com.bbubbush.board.annotation;

import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;

public interface DynamicMapper<T> extends CommonCountMapper, CommonInsertMapper<T>, CommonDeleteMapper, CommonUpdateMapper {

}
