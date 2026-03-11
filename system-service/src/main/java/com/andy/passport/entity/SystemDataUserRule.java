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
 * 用户数据权限规则表
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Getter
@Setter
@TableName("system_data_user_rule")
@Schema(description = "用户数据权限规则表")
public class SystemDataUserRule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "人员ID")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "资源ID")
    @TableField("data_id")
    private Integer dataId;

    @Schema(description = "数据权限规则（枚举）：ALL/CHILDREN/SELF")
    @TableField("rule_type")
    private String ruleType;

}
