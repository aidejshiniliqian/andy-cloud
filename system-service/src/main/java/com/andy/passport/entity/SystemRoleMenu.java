package com.andy.passport.entity;

import com.andy.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色功能菜单表
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Getter
@Setter
@TableName("system_role_menu")
@Schema(description = "角色功能菜单表")
public class SystemRoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
