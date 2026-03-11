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
 * 用户组织机构表
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Getter
@Setter
@TableName("system_user_org")
@Schema(description = "用户组织机构表")
public class SystemUserOrg extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "组织机构ID")
    @TableField("org_id")
    private Integer orgId;

}
