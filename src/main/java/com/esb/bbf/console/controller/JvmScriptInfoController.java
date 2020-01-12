package com.esb.bbf.console.controller;

import com.esb.bbf.console.domain.JvmScriptInfo;
import com.esb.bbf.console.domain.query.PageQuery;
import com.esb.bbf.console.domain.query.PageRes;
import com.esb.bbf.console.service.JvmScriptInfoService;
import com.esb.bbf.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * User:
 * Date18-6-11
 * Time: 下午3:39
 */
@Controller
@RequestMapping("/console/jvmScriptInfo")
public class JvmScriptInfoController {
    @Autowired
    private JvmScriptInfoService jvmScriptInfoService;


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public R save(@Valid JvmScriptInfo jvmScriptInfo) {
        jvmScriptInfoService.save(jvmScriptInfo);
        return R.ok();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R update(@Valid JvmScriptInfo jvmScriptInfo) {
        jvmScriptInfoService.updateWithBack(jvmScriptInfo);
        return R.ok();
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R list(PageQuery<JvmScriptInfo> pageQuery, JvmScriptInfo jvmScriptInfo) {
        pageQuery.setQuery(jvmScriptInfo);
        pageQuery.setStartRow((pageQuery.getPageNo() - 1) * pageQuery.getPageSize());
        List<JvmScriptInfo> jvmScriptInfoList = jvmScriptInfoService.queryBySelectiveForPagination(pageQuery);

        Long count = jvmScriptInfoService.queryCountBySelective(pageQuery.getQuery());

        PageRes pageRes = new PageRes(jvmScriptInfoList, count == null ? 0 : count.intValue(), pageQuery);

        return R.ok().put("page", pageRes);
    }

    @ResponseBody
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        JvmScriptInfo jvmScriptInfo = jvmScriptInfoService.queryByPrimaryKey(id);
        return R.ok().put("jvmScriptInfo", jvmScriptInfo);
    }


}
