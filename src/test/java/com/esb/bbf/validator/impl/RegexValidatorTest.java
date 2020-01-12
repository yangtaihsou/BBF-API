package com.esb.bbf.validator.impl;

import com.esb.bbf.validator.ValidatorConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class RegexValidatorTest {
    @Test
    public void test(){
        RegexValidator regexValidator = new RegexValidator();
        ValidatorConfig config = new ValidatorConfig();
        config.setValue("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        boolean result = regexValidator.isValid("yangkuan@yeah.net",config);
        Assert.assertTrue(result);
        result = regexValidator.isValid("yangkuan@yeah.com",config);
        Assert.assertTrue(result);
        result = regexValidator.isValid("yangkuan@yeah.cn",config);
        Assert.assertTrue(result);
        result = regexValidator.isValid("yangkuan@yeah.com.cn",config);
        Assert.assertTrue(result);
        result = regexValidator.isValid("yangkuan@yeah",config);
        Assert.assertTrue(!result);
        config.setValue("^(13[4,5,6,7,8,9]|15[0,3,8,9,1,7]|188|187)\\d{8}$");
        result = regexValidator.isValid("15300270491",config);
        Assert.assertTrue(result);
        result = regexValidator.isValid("yangkuan@yeah",config);
        Assert.assertTrue(!result);
    }
}
