package com.esb.bbf.validator.impl;


import com.esb.bbf.validator.Validator;
import com.esb.bbf.validator.ValidatorConfig;

public class NotNullValidator implements Validator {

    @Override
    public boolean isValid(Object object, ValidatorConfig config) {
        if (object == null) {
            return false;
        }
        if (object instanceof String) {
            int length = ((String) object).length();
            return length > 0;
        }
        return true;
    }
}
