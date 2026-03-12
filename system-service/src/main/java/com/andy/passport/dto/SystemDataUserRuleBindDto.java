package com.andy.passport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SystemDataUserRuleBindDto {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "数据规则列表")
    private List<SystemDataRuleItemDto> dataRules;

    @Data
    public static class SystemDataRuleItemDto {

        @Schema(description = "资源ID")
        private Integer dataId;

        @Schema(description = "数据权限规则（枚举）：ALL/CHILDREN/SELF")
        private String ruleType;
    }
}
