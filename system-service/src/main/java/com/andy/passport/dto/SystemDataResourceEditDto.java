package com.andy.passport.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemDataResourceEditDto {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "数据资源名称")
    private String dataName;

    @Schema(description = "数据资源表名（物理表）")
    private String dataTable;

    @Schema(description = "排序")
    private Integer sort;

}
