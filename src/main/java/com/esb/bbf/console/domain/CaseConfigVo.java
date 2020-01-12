package com.esb.bbf.console.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaseConfigVo {

    /**
     *
     */
    private Long id;
    /**
     * 通过类名作为case名字
     */

    @NotEmpty(message = "类名不能为空")
    private String className;
    /**
     * case的内容
     */

    @NotEmpty(message = "case的内容不能为空")
    private String caseText;

    /**
     * case脚本语言类型
     */

    @NotEmpty(message = "脚本类型不能为空")
    private String scriptType;
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

    private String systemCode;
    /**
     * 系统名字
     */

    private String systemName;
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
    private String method;
    private String inputPara;

}
