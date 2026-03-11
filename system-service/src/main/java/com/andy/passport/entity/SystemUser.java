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
 * 用户表
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Getter
@Setter
@TableName("system_user")
@Schema(description = "用户表")
public class SystemUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户账号")
    @TableField("user_code")
    private String userCode;

    @Schema(description = "用户名称")
    @TableField("user_name")
    private String userName;

    @Schema(description = "国家区号")
    @TableField("country_code")
    private String countryCode;

    @Schema(description = "国家名")
    @TableField("country_name")
    private String countryName;

    @Schema(description = "用户密码")
    @TableField("password")
    private String password;

    @Schema(description = "手机号")
    @TableField("mobile")
    private String mobile;

    @Schema(description = "用户邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "性别")
    @TableField("sex")
    private String sex;

    @Schema(description = "头像")
    @TableField("head_pic")
    private String headPic;

    @Schema(description = "签名图片")
    @TableField("sign_pic")
    private String signPic;

    @Schema(description = "用户地址")
    @TableField("address")
    private String address;

    @Schema(description = "员工编号")
    @TableField("number")
    private String number;

    @Schema(description = "任职状态Value")
    @TableField("office_status_value")
    private String officeStatusValue;

    @Schema(description = "任职状态Code")
    @TableField("office_status_code")
    private String officeStatusCode;

    @Schema(description = "职务名称")
    @TableField("post_name")
    private String postName;

    @Schema(description = "备注")
    @TableField("remarks")
    private String remarks;

    @Schema(description = "人员排序")
    @TableField("sort")
    private Integer sort;

}
