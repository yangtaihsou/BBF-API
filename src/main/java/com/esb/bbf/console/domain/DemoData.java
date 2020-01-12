package com.esb.bbf.console.domain;

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
public class DemoData {
    private Long age;
    private String name;
    private Integer sex;
    private List<String> bookList;
    private String userId;
    private String userName;
}
