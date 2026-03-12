package com.andy.passport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemDataUserRuleEditDto {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "人员ID")
    private Integer userId;

    @Schema(description = "资源ID")
    private Integer dataId;

    @Schema(description = "数据权限规则（枚举）：ALL/CHILDREN/SELF")
    private String ruleType;

}
