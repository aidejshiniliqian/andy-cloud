package com.andy.passport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemDataResourceVo {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "数据资源名称")
    private String dataName;

    @Schema(description = "数据资源表名（物理表）")
    private String dataTable;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态，0 ==正常，1==禁用，2==删除")
    private Integer status;
}
