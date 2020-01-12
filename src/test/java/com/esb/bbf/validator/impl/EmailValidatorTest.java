package com.esb.bbf.validator.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class EmailValidatorTest {
    @Test
    public void test(){
        EmailValidator emailValidator = new EmailValidator();
        boolean result = emailValidator.isValid("yangkuan@yeah.net",null);
        Assert.assertTrue(result);
        result = emailValidator.isValid("yangkuan@yeah.com",null);
        Assert.assertTrue(result);
        result = emailValidator.isValid("yangkuan@yeah.cn",null);
        Assert.assertTrue(result);
        result = emailValidator.isValid("yangkuan@yeah.com.cn",null);
        Assert.assertTrue(result);
        result = emailValidator.isValid("yangkuan@yeah",null);
        Assert.assertTrue(!result);
    }
}
