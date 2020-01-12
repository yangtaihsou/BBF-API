package com.esb.bbf.console.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 涵盖脚本信息、接口信息等
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppDetailInfo {
    private String appId;
    private Long id;
    /**
     * 执行Id
     */
    private String detailUuid;
    private String detailName;

    /**
     * 排序
     */
    private Integer indexOrder;
    /**
     * 备注
     */
    private String remark;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 类型。脚本、接口
     */
    private Integer type;
    /**
     * 类型。脚本、接口
     */
    private String typeDesc;

}
