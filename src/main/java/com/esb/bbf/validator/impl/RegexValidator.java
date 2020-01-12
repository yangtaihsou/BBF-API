package com.esb.bbf.validator.impl;

import com.esb.bbf.validator.Validator;
import com.esb.bbf.validator.ValidatorConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator implements Validator {
    @Override
    @SuppressWarnings("Duplicates")
    public boolean isValid(Object object, ValidatorConfig config) {
        if (object == null) {
            return true;
        }
        String check = (String) config.getValue();
        Pattern regex = Pattern.compile(check);
        if (object instanceof String) {
            Matcher matcher = regex.matcher((String) object);
            return matcher.matches();
        } else {
            return false;
        }
    }
}
