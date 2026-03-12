package com.andy.passport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "用户数据权限绑定DTO")
public class SystemDataUserRuleBindDto {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "数据权限规则列表")
    private List<DataRuleItem> dataRules;

    @Data
    @Schema(description = "数据权限规则项")
    public static class DataRuleItem {
        @Schema(description = "资源ID")
        private Integer dataId;

        @Schema(description = "数据权限规则（枚举）：ALL/CHILDREN/SELF")
        private String ruleType;
    }
}
