package com.esb.bbf.validator;

import org.junit.Assert;
import org.junit.Test;

public class ValidatorEnumTest {
    @Test
    public void test(){
        boolean result =   ValidatorEnum.emailValidator.getValidator().isValid("yangkuan@yeah.net",null);
        Assert.assertTrue(result);

        result =  ValidatorEnum.emailValidator.getValidator().isValid("yangkuan@yeah",null);
        Assert.assertTrue(!result);
    }
    @Test
    public void testGetName(){

        boolean result =   ValidatorEnum.getValidator("EmailValidator").isValid("yangkuan@yeah.net",null);
        Assert.assertTrue(result);

        result =   ValidatorEnum.getValidator("EmailValidator").isValid("yangkuan@yeah",null);
        Assert.assertTrue(!result);

        Validator validator =   ValidatorEnum.getValidator("test");
        Assert.assertNull(validator);

    }
}
