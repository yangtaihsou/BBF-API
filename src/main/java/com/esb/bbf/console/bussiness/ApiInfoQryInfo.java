package com.esb.bbf.console.bussiness;

import com.esb.bbf.console.domain.AppInfo;
import com.esb.bbf.console.domain.JvmScriptInfo;
import com.esb.bbf.console.domain.AppDemoInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiInfoQryInfo {
    private AppInfo appInfo;
    private List<JvmScriptInfo> jvmScriptInfoList;
    private AppDemoInfo appDemoInfo;
}
