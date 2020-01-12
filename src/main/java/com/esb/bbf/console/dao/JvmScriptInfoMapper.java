package com.esb.bbf.console.dao;

import com.esb.bbf.console.domain.JvmScriptInfo;
import com.esb.bbf.console.domain.query.PageQuery;
import com.esb.bbf.console.domain.query.Query;

import java.util.List;

public interface JvmScriptInfoMapper {
    List<JvmScriptInfo> queryBySelective(Query<JvmScriptInfo> jvmScriptInfo);

    long queryCountBySelective(JvmScriptInfo jvmScriptInfo);

    JvmScriptInfo queryByPrimaryKey(Long id);

    Integer save(JvmScriptInfo jvmScriptInfo);

    List<JvmScriptInfo> queryBySelectiveForPagination(PageQuery<JvmScriptInfo> jvmScriptInfo);

    Integer updateByPrimaryKey(JvmScriptInfo jvmScriptInfo);

    JvmScriptInfo queryByScriptId(String scriptId);

}
