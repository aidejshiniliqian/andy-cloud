package com.andy.passport.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemSubSystemEditDto {

    @Schema(description = "id")
    private Integer id;

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
