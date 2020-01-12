package com.esb.bbf.console.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    //请求时，url路径变量填充值
    private Map<String, Object> uriVariables;
    //请求时传递给的值，比如json串，或者form值
    private Object body;

    //http header
    private Map<String, String> headers;
}
