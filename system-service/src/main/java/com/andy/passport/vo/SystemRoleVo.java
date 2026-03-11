package com.andy.passport.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemRoleVo {

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

    @Schema(description = "状态，0 ==正常，1==禁用，2==删除")
    @TableField("status")
    private Integer status;
}
