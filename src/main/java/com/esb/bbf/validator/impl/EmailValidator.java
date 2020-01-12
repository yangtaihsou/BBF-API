package com.esb.bbf.validator.impl;

import com.esb.bbf.validator.Validator;
import com.esb.bbf.validator.ValidatorConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Validator {
    @Override
    @SuppressWarnings("Duplicates")
    public boolean isValid(Object object, ValidatorConfig config) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            if (((String)object).equals("")) {
                return true;
            }
        }
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        if (object instanceof String) {
            Matcher matcher = regex.matcher((String) object);
            return matcher.matches();
        } else {
            return false;
        }
    }
}
