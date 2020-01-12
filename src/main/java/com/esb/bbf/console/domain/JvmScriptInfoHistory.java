/**
 * Copyright(c) 2004-
 * JvmScriptInfoHistory.java
 */
package com.esb.bbf.console.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


/**
 * 
 * @author 
 * @date 
 * app脚本配置
 *
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JvmScriptInfoHistory implements Serializable {

    /**
     * 
     */
     private Long id;
    /**
     * 脚本编号
     */
     private String scriptId;
    /**
     * 脚本作用名字
     */
     private String scriptName;
    /**
     * 脚本内容
     */
     private String scriptText;
    /**
     * case脚本语言类型
     */
     private String scriptType;
    /**
     * apiId
     */
     private String appId;
    /**
     * 备注
     */
     private String remark;
    /**
     * 版本
     */
     private String version;
    /**
     * 排序
     */
     private Integer indexOrder;
    /**
     * 演示脚本的输入参数
     */
     private String demoInputPara;
    /**
     * 脚本样式
     */
     private String themeType;
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
