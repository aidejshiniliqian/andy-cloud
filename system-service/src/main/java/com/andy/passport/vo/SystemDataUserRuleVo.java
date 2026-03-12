package com.andy.passport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户数据权限规则VO")
public class SystemDataUserRuleVo {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "资源ID")
    private Integer dataId;

    @Schema(description = "数据资源名称")
    private String dataName;

    @Schema(description = "数据资源表名")
    private String dataTable;

    @Schema(description = "数据权限规则（枚举）：ALL/CHILDREN/SELF")
    private String ruleType;
}
