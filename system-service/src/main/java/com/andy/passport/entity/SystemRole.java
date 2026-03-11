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
 * 系统角色表
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Getter
@Setter
@TableName("system_role")
@Schema(description = "系统角色表")
public class SystemRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
