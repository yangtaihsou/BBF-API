package com.esb.bbf.console.controller;

import com.esb.bbf.console.dao.JvmScriptInfoHistoryMapper;
import com.esb.bbf.util.R;
import com.esb.bbf.console.domain.JvmScriptInfoHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User:
 * Date18-6-11
 * Time: 下午3:39
 */
@Controller
@RequestMapping("/console/jvmScriptInfoHistory")
public class JvmScriptInfoHistoryController {

    @Autowired
    private JvmScriptInfoHistoryMapper jvmScriptInfoHistoryMapper;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R list(@RequestParam(value="scriptId",required=true)String scriptId) {
        List<JvmScriptInfoHistory> list =
                jvmScriptInfoHistoryMapper.queryByScriptId(scriptId);
        return R.ok().put("data", list);
    }

    @ResponseBody
    @RequestMapping(value ="/info", method = RequestMethod.GET)
    public R info(@RequestParam(value="id",required=true) Long id) {
        JvmScriptInfoHistory jvmScriptInfoHistory = jvmScriptInfoHistoryMapper.queryByPrimaryKey(id);
        return R.ok().put("jvmScriptInfo", jvmScriptInfoHistory);
    }


}
