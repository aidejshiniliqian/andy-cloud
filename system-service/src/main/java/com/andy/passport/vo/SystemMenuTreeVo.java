package com.andy.passport.vo;

import com.andy.common.utils.BaseTree;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SystemMenuTreeVo extends BaseTree<SystemMenuTreeVo> {

    @Schema(description = "id")
    private String id;

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

    @Schema(description = "状态，0 ==正常，1==禁用，2==删除")
    private Integer status;
}
