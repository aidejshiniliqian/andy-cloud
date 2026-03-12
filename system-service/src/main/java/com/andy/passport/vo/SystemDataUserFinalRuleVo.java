package com.andy.passport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemDataUserFinalRuleVo {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "资源ID")
    private Integer dataId;

    @Schema(description = "数据权限规则（枚举）：ALL/CHILDREN/SELF")
    private String ruleType;

    @Schema(description = "数据权限来源：USER-用户授权，ROLE-角色授权")
    private String sourceType;

    @Schema(description = "数据资源名称")
    private String dataName;

    @Schema(description = "数据资源表名")
    private String dataTable;

}
