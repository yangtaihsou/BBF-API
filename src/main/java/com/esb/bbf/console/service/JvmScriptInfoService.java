package com.esb.bbf.console.service;

import com.esb.bbf.console.domain.JvmScriptInfo;

public interface JvmScriptInfoService  extends BaseService<JvmScriptInfo>{


    void updateWithBack(JvmScriptInfo jvmScriptInfo);
}
