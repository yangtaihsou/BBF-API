package com.esb.bbf.validator;

public interface Validator<T> {
    /**
     * @param t 参数值
     * @return
     */
    boolean isValid(T t, ValidatorConfig config);
}
