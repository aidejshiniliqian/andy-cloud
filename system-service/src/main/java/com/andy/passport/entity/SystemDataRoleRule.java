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
 * 角色数据权限规则表
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Getter
@Setter
@TableName("system_data_role_rule")
@Schema(description = "角色数据权限规则表")
public class SystemDataRoleRule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    @TableField("role_id")
    private Integer roleId;

    @Schema(description = "资源ID")
    @TableField("data_id")
    private Integer dataId;

    @Schema(description = "数据权限规则（枚举）：ALL/CHILDREN/SELF")
    @TableField("rule_type")
    private String ruleType;

}