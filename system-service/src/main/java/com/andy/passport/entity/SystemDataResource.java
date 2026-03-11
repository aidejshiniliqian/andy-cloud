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
 * 数据资源表
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Getter
@Setter
@TableName("system_data_resource")
@Schema(description = "数据资源表")
public class SystemDataResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "数据资源名称")
    @TableField("data_name")
    private String dataName;

    @Schema(description = "数据资源表名（物理表）")
    @TableField("data_table")
    private String dataTable;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

}
