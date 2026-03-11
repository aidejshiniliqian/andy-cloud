package com.andy.passport.service.impl;

import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemRoleMenuEditDto;
import com.andy.passport.entity.SystemRole;
import com.andy.passport.entity.SystemRoleMenu;
import com.andy.passport.mapper.SystemRoleMenuMapper;
import com.andy.passport.service.SystemRoleMenuService;
import com.andy.passport.service.SystemRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色功能菜单表 服务实现类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Service
public class SystemRoleMenuServiceImpl extends ServiceImpl<SystemRoleMenuMapper, SystemRoleMenu> implements SystemRoleMenuService {

    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 查询角色下的菜单列表
     * @param roleId
     * @return
     */
    @Override
    public List<SystemRoleMenu> queryRoleMenus(Integer roleId) {
        LambdaQueryWrapper<SystemRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemRoleMenu::getRoleId, roleId);
        return super.list(queryWrapper);
    }

    /**
     * 编辑角色功能菜单
     * @param systemRoleMenuEditDto
     * @return
     */
    @Override
    public Boolean editRoleMenu(SystemRoleMenuEditDto systemRoleMenuEditDto) {
        SystemRole systemRole = systemRoleService.getById(systemRoleMenuEditDto.getRoleId());
        if(null == systemRole){
            throw new BusinessException("数据错误");
        }
        //先删除角色下的菜单（物理删除）
        LambdaQueryWrapper<SystemRoleMenu> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SystemRoleMenu::getRoleId, systemRoleMenuEditDto.getRoleId());
        super.remove(deleteWrapper);
        //批量添加
        List<SystemRoleMenu> systemRoleMenus = new ArrayList<>();
        systemRoleMenuEditDto.getMenuIds().forEach(menuId -> {
            SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
            systemRoleMenu.setRoleId(systemRoleMenuEditDto.getRoleId());
            systemRoleMenu.setSubSystemCode(systemRoleMenuEditDto.getSubSystemCode());
            systemRoleMenu.setMenuId(menuId);
//            systemRoleMenu.setCreateBy();
            systemRoleMenu.setCreateTime(new Date());
            systemRoleMenus.add(systemRoleMenu);
        });
        super.saveBatch(systemRoleMenus);
        return true;
    }
}
