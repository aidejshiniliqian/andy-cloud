package com.andy.passport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemDataUserRuleVo {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "人员ID")
    private Integer userId;

    @Schema(description = "资源ID")
    private Integer dataId;

    @Schema(description = "数据权限规则（枚举）：ALL/CHILDREN/SELF")
    private String ruleType;

    @Schema(description = "状态，0 ==正常，1==禁用，2==删除")
    private Integer status;

}
