package com.esb.bbf.validator.impl;
import com.esb.bbf.validator.Validator;
import com.esb.bbf.validator.ValidatorConfig;
public class SizeValidator implements Validator {
    @Override
    public boolean isValid(Object object, ValidatorConfig config) {
        if (object == null) {
            return true;
        }
        Integer max = config.getMax();
        Integer min = config.getMin();
        if (object instanceof String) {
            int length = ((String) object).length();
            return length >= min && length <= max;
        }
        return true;
    }
}
