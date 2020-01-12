package com.esb.bbf.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestParaCheckConfig {
    private String name;
    private String config;
    private List<ValidatorConfig> validatorConfigList;
}
