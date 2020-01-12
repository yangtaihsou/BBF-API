package com.esb.bbf.console.dao;

import com.esb.bbf.console.domain.query.PageQuery;
import com.esb.bbf.console.domain.query.Query;
import com.esb.bbf.console.domain.JvmScriptInfoHistory;

import java.util.List;

public interface JvmScriptInfoHistoryMapper {
    List<JvmScriptInfoHistory> queryBySelective(Query<JvmScriptInfoHistory> jvmScriptInfoHistory);

    long queryCountBySelective(JvmScriptInfoHistory jvmScriptInfoHistory);

    JvmScriptInfoHistory queryByPrimaryKey(Long id);

    Integer save(JvmScriptInfoHistory jvmScriptInfoHistory);

    List<JvmScriptInfoHistory> queryBySelectiveForPagination(PageQuery<JvmScriptInfoHistory> jvmScriptInfoHistory);
    List<JvmScriptInfoHistory> queryByScriptId(String scriptId);
}
