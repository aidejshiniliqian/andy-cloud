package com.andy.passport.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemOrgEditDto {

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "组织机构编码")
    @TableField("org_code")
    private String orgCode;

    @Schema(description = "组织机构名称")
    @TableField("org_name")
    private String orgName;

    @Schema(description = "上级ID")
    @TableField("parent_id")
    private Integer parentId;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "状态，0 ==正常，1==禁用，2==删除")
    private Integer status;
}
