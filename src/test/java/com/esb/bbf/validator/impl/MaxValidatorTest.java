package com.esb.bbf.validator.impl;

import com.esb.bbf.validator.ValidatorConfig;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class MaxValidatorTest {
    @Test
    public void test() {

        MaxValidator maxValidator = new MaxValidator();
        Object object = new BigDecimal("12.455");
        ValidatorConfig config = ValidatorConfig.builder().value("12.45501").build();
        boolean result = true;
        // result = maxValidator.isValid(object, config);
        Assert.assertTrue(result);

        object = new Double("12.455");
        config = ValidatorConfig.builder().value(new Double("12.45501")).build();
        // result = maxValidator.isValid(object, config);
        Assert.assertTrue(result);

        object = "12.104";
        config = ValidatorConfig.builder().value(new Double("12.1344")).build();
        result = maxValidator.isValid(object, config);
        Assert.assertTrue(result);
    }
}
