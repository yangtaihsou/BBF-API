package com.esb.bbf.validator;

import com.esb.bbf.validator.impl.MaxValidator;
import com.esb.bbf.validator.impl.MinValidator;
import com.esb.bbf.validator.impl.NotNullValidator;
import com.esb.bbf.validator.impl.RegexValidator;
import com.esb.bbf.validator.impl.EmailValidator;
import com.esb.bbf.validator.impl.SizeValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ValidatorEnum {
    emailValidator("EmailValidator","邮箱验证器", new EmailValidator()),
    maxValidator("MaxValidator", "数字最大验证器",new MaxValidator()),
    minValidator("MinValidator","数字最小验证器", new MinValidator()),
    notNullValidator("NotNullValidator", "不为空验证器",new NotNullValidator()),
    regexValidator("RegexValidator","正则验证器", new RegexValidator()),
    sizeValidator("SizeValidator", "长度验证器",new SizeValidator()),;
    private String name;
    private String desc;
    private Validator validator;

    public static Validator getValidator(String name){
        //ValidatorEnum validatorEnum =  ValidatorEnum.valueOf(name);
        for (ValidatorEnum validatorEnum : ValidatorEnum.values()) {
             if(validatorEnum.getName().equals(name)){
                 return validatorEnum.getValidator();
             }
        }
        return null;
    }
}
