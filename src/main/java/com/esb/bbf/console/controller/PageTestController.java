package com.esb.bbf.console.controller;

import com.esb.bbf.console.domain.DemoData;
import com.esb.bbf.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/test/page")
@Slf4j
public class PageTestController {
    @RequestMapping(value = "/uuid", method = RequestMethod.GET)
    @ResponseBody
    public R getUuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return R.ok().put("uuid", uuid);
    }

    @RequestMapping(value = "/postFormData", method = RequestMethod.POST)
    @ResponseBody
    public R testPostFormData(
            @RequestParam final Long age,
            @RequestParam final String name) {
        log.info("age={},name={}", age, name);
        return R.ok().put("age", age).put("name", name);
    }

    /**
     * formdata格式转化为vo
     *
     * @param demoData
     * @return
     */
    @RequestMapping(value = "/postFormDataToJson", method = RequestMethod.POST)
    @ResponseBody
    public R postFormDataToJson(DemoData demoData) {
        log.info("demoData,age={},name={}", demoData.getAge(), demoData.getName());
        return R.ok().put("data", demoData);
    }

    /**
     * formdata格式转化为vo
     *
     * @param demoData
     * @return
     */
    @RequestMapping(value = "/postToJson", method = RequestMethod.POST)
    @ResponseBody
    public R postToJson(@RequestBody DemoData demoData) {
        log.info("demoData,age={},name={},sex={}",
                demoData.getAge(),
                demoData.getName(),
                demoData.getSex());
        return R.ok().put("data", demoData);
    }

    @RequestMapping(value = "/testQuestParaList", method = RequestMethod.POST)
    @ResponseBody
    public R testRequestParaList(@RequestBody List<DemoData> demoDatas) {
        return R.ok().put("data", demoDatas);
    }

    @RequestMapping(value = "/testResponseParaList", method = RequestMethod.POST)
    @ResponseBody
    public DemoData testResponseParaList(@RequestBody DemoData demoData) {
/*        List<String> books = new ArrayList<>();
        books.add("史记");
        books.add("诗经");
        demoData.setBookList(books);*/
        return demoData;
    }


}
