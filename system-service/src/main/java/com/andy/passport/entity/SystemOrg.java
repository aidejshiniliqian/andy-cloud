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
 * 组织机构表
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Getter
@Setter
@TableName("system_org")
@Schema(description = "组织机构表")
public class SystemOrg extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "上级ID")
    @TableField("parent_id")
    private Integer parentId;

    @Schema(description = "树形层级")
    @TableField("level")
    private Integer level;

    @Schema(description = "机构编号")
    @TableField("org_code")
    private String orgCode;

    @Schema(description = "机构名称")
    @TableField("org_name")
    private String orgName;

    @Schema(description = "组织机构属性名称")
    @TableField("org_attribute_value")
    private String orgAttributeValue;

    @Schema(description = "组织机构属性01总公司 02分公司 03子公司 04部门")
    @TableField("org_attribute_code")
    private String orgAttributeCode;

    @Schema(description = "当前层级排序：1")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "组织机构编号路径：/1/3/5")
    @TableField("path")
    private String path;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "附件")
    @TableField("attachment")
    private String attachment;
}
