package com.andy.passport.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SystemRoleMenuEditDto {

    @Schema(description = "角色id")
    private Integer roleId;

    @Schema(description = "子系统code：包含子系统和APP")
    @TableField("sub_system_code")
    private String subSystemCode;

    @Schema(description = "菜单ids")
    private List<Integer> menuIds;



}
