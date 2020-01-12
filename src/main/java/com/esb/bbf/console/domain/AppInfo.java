/**
 * Copyright(c) 2004-
 * com.pmock.server.vo.AppInfo.java
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
 * @date app配置
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppInfo implements Serializable {

    /**
     *
     */
    private Long id;
    /**
     * app编号
     */
    @NotEmpty(message = "app编号不能为空")
    private String appId;
    /**
     * 名字
     */
    @NotEmpty(message = "app名字不能为空")
    private String appName;
    /**
     * 路径
     */
    private String appPath;
    /**
     * 检查配置
     */
    private String checkPara;
    /**
     * 参数
     */
    private String appPara;
    /**
     * post|get等
     */
    private String action;
    /**
     * 备注
     */
    @NotEmpty(message = "app备注不能为空")
    private String remark;
    /**
     * 版本
     */
    private String version;
    /**
     * 配置人id
     */
    private String userId;
    /**
     * 配置人名字
     */
    private String userName;
    /**
     * 系统编码
     */
    @NotEmpty(message = "系统编码不能为空")
    private String systemCode;
    /**
     * 系统名字
     */
    private String systemName;
    /**
     * 状态
     */
    private Integer status;
    private String userIds;
    private String lastModifyUser;
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
