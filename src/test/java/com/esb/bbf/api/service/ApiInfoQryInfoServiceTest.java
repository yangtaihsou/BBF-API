package com.esb.bbf.api.service;

import com.esb.bbf.console.bussiness.ApiInfoQryInfo;
import org.junit.Assert;
import org.junit.Test;

public class ApiInfoQryInfoServiceTest {
    @Test
    public void test(){
        ApiInfoQryInfoServiceImpl apiInfoQryInfoService = new ApiInfoQryInfoServiceImpl();
        ApiInfoQryInfo apiInfoQryInfo = apiInfoQryInfoService.readFromDisk("1183ffb3046e4a72b8766a57e4260819");
        Assert.assertNull(apiInfoQryInfo);
        apiInfoQryInfo = apiInfoQryInfoService.readFromDisk("e14612d2456840eaa860101d75f6ee5b");
        Assert.assertNotNull(apiInfoQryInfo);

        apiInfoQryInfo = apiInfoQryInfoService.readFromDisk("1");
        Assert.assertNull(apiInfoQryInfo);
    }
}
