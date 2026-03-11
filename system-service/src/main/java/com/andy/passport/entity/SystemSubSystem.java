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
 * 子系统
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-03-03
 */
@Getter
@Setter
@TableName("system_sub_system")
@Schema(description = "子系统")
public class SystemSubSystem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "子系统名称")
    @TableField("sub_system_name")
    private String subSystemName;

    @Schema(description = "子系统编码")
    @TableField("sub_system_code")
    private String subSystemCode;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;
}
