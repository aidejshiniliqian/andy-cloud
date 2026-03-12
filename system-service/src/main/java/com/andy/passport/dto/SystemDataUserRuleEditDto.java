package com.andy.passport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SystemDataUserRuleEditDto {

    @Schema(description = "人员ID")
    private Integer userId;

    @Schema(description = "资源ID列表")
    private List<Integer> dataIds;

    @Schema(description = "数据权限规则（枚举）：ALL/CHILDREN/SELF")
    private String ruleType;

}
