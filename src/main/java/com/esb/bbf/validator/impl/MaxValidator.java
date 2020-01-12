package com.esb.bbf.validator.impl;

import com.esb.bbf.validator.Validator;
import com.esb.bbf.validator.ValidatorConfig;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
public class MaxValidator implements Validator {


    @Override
    @SuppressWarnings("Duplicates")
    public boolean isValid(Object object, ValidatorConfig config) {
        //null values are valid
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            int length = ((String) object).length();
            if (length == 0) {
                return true;
            }
        }

        try {
            Object value = config.getValue();
            if (value == null) {
                return false;
            }
            if (object instanceof BigDecimal) {
                return ((BigDecimal) object).compareTo(new BigDecimal(value.toString())) != 1;
            } else if (object instanceof BigInteger) {
                return ((BigInteger) object).compareTo(new BigInteger(value.toString())) != 1;
            } else if (object instanceof Double) {
                return ((Double) object).compareTo(new Double(value.toString())) != 1;
            }else if (object instanceof Long) {
                return ((Long) object).compareTo(Long.valueOf(value.toString())) != 1;
            } else if (object instanceof Integer) {
                return ((Integer) object).compareTo(Integer.valueOf(value.toString())) != 1;
            } else if (object instanceof String) {
                return (new BigDecimal((String) object)).compareTo(new BigDecimal(value.toString())) != 1;
            }
        } catch (Exception e) {
            log.error(config.getMessage() + "验证大小时，输入的不是数字。");
            return false;
        }
        return true;
    }
}
