package com.andy.passport.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SystemUserQueryDto {

    @Schema(description = "用户账号")
    @TableField("user_code")
    private String userCode;

    @Schema(description = "用户名称")
    @TableField("user_name")
    private String userName;

    @Schema(description = "手机号")
    @TableField("mobile")
    private String mobile;

    @Schema(description = "员工编号")
    @TableField("number")
    private String number;
}
