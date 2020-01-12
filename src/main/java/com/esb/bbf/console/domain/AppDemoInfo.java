/**
 * Copyright(c) 2004-
 * AppDemoInfo.java
 */
package com.esb.bbf.console.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;


/**
 * @author
 * @date app展示信息
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppDemoInfo implements Serializable {
    private Long id;
    /**
     * app编号
     */
    @NotEmpty(message = "app编号不能为空")
    private String appId;
    /**
     * 名字
     */
    private String appName;
    /**
     * url
     */
    @NotEmpty(message = "url不能为空")
    private String url;
    /**
     * body
     */
    private String body;
    /**
     * contentType
     */
    private String contentType;
    /**
     * header
     */
    private String header;
    /**
     * formData
     */
    private String formData;
    /**
     * method
     */
    private String method;
    /**
     * mock信息
     */
    private String mockInfo;
    /**
     * 是否mock调试
     */
    private Boolean mocked;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 扩展字段1
     */
    private String ext1;
    /**
     * 扩展字段2
     */
    private String ext2;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 创建时间
     */
    private Date createdDate;


}
