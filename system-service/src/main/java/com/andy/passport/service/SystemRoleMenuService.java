package com.andy.passport.service;

import com.andy.passport.dto.SystemRoleMenuEditDto;
import com.andy.passport.entity.SystemRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色功能菜单表 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemRoleMenuService extends IService<SystemRoleMenu> {

    List<SystemRoleMenu> queryRoleMenus(Integer roleId);

    Boolean editRoleMenu(SystemRoleMenuEditDto systemRoleMenuEditDto);
}
