package com.esb.bbf.console.service;

import com.esb.bbf.console.domain.query.PageQuery;
import com.esb.bbf.console.domain.query.Query;

import java.util.List;

public interface BaseService<T> {

    List<T> queryBySelective(Query<T> query);

    Long queryCountBySelective(T t);

    T queryByPrimaryKey(Long id);

    Boolean save(T t);

    List<T> queryBySelectiveForPagination(PageQuery<T> t);

    void updateByPrimaryKey(T t);
}
