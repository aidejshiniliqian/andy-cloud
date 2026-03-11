package com.andy.passport.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemRoleEditDto {

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "角色名称")
    @TableField("role_name")
    private String roleName;

    @Schema(description = "角色编号")
    @TableField("role_code")
    private String roleCode;

    @Schema(description = "99:超级管理员；01常规角色；")
    @TableField("role_type")
    private String roleType;

    @Schema(description = "角色描述")
    @TableField("remark")
    private String remark;

    @Schema(description = "优先级标识")
    @TableField("priority")
    private Integer priority;


}
