package com.esb.bbf.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidatorConfig {
    /**
     * 提示信息
     */
    private String message;
    private Object value;
    private String scope;
    private Integer max;
    private Integer min;
    /**
     * 验证类型，如NotNull
     */
    private String validator;
}
