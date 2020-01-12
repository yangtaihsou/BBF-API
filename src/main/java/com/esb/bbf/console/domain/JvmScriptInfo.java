/**
 * Copyright(c) 2004-
 * com.pmock.server.vo.JvmScriptInfo.java
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
 * @date app脚本配置
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JvmScriptInfo implements Serializable {

    private Long id;
    /**
     * 脚本编号
     */
    @NotEmpty(message = "脚本编号不能为空")
    private String scriptId;
    /**
     * 脚本名字
     */
    @NotEmpty(message = "脚本名字不能为空")
    private String scriptName;
    /**
     * 脚本内容
     */
    @NotEmpty(message = "脚本内容不能为空")
    private String scriptText;
    /**
     * 脚本类型
     */
    @NotEmpty(message = "脚本类型不能为空")
    private String scriptType;

    /**
     * appId
     */
    @NotEmpty(message = "appId不能为空")
    private String appId;
    /**
     * 版本
     */
    private String version;
    /**
     * 排序
     */
    private Integer indexOrder;
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
    /**
     * 演示脚本的输入参数
     */
    private String demoInputPara;
    /**
     * 脚本样式
     */
    private String themeType;

    /**
     * 备注
     */
    private String remark;

}
