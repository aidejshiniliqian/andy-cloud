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
 * 功能菜单表
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Getter
@Setter
@TableName("system_menu")
@Schema(description = "功能菜单表")
public class SystemMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "子系统code：包含子系统和APP")
    @TableField("sub_system_code")
    private String subSystemCode;

    @Schema(description = "资源名称")
    @TableField("menu_name")
    private String menuName;

    @Schema(description = "上级ID")
    @TableField("parent_id")
    private Integer parentId;

    @Schema(description = "是否是路由：1是，0不是")
    @TableField("is_route")
    private Integer isRoute;

    @Schema(description = "资源url")
    @TableField("menu_url")
    private String menuUrl;

    @Schema(description = "图标")
    @TableField("ioc")
    private String ioc;

    @Schema(description = "vue路由地址")
    @TableField("path")
    private String path;

    @Schema(description = "权限标识 0表示一级菜单 1表示二级菜单 2表示三级菜单，3表示资源按钮")
    @TableField("type")
    private Integer type;

    @Schema(description = "层级")
    @TableField("level")
    private Integer level;

    @Schema(description = "页面是否缓存")
    @TableField("cached")
    private Integer cached;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "权限url")
    @TableField("permission_url")
    private String permissionUrl;

}
