package com.andy.passport.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemRoleMenuVo {

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "角色ID")
    @TableField("role_id")
    private Integer roleId;

    @Schema(description = "子系统code：包含子系统和APP")
    @TableField("sub_system_code")
    private String subSystemCode;

    @Schema(description = "权限ID")
    @TableField("menu_id")
    private Integer menuId;
}
