package com.andy.passport.vo;

import com.andy.common.utils.BaseTree;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemOrgTreeVo extends BaseTree<SystemOrgTreeVo> {

    @Schema(description = "ID")
    private String id;

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

    @Schema(description = "组织机构路径：/1/3/5")
    @TableField("path")
    private String path;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "附件")
    @TableField("attachment")
    private String attachment;

    @Schema(description = "状态，0 ==正常，1==禁用，2==删除")
    @TableField("status")
    private Integer status;
}
