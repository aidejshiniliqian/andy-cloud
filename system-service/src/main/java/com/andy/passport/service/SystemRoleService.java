package com.andy.passport.service;

import com.andy.passport.dto.SystemRoleEditDto;
import com.andy.passport.dto.SystemRoleQueryDto;
import com.andy.passport.entity.SystemRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemRoleService extends IService<SystemRole> {

    IPage<SystemRole> page(Page<SystemRole> page, SystemRoleQueryDto systemRoleQueryDto);

    Integer edit(SystemRoleEditDto systemRoleEditDto);

    boolean checkRoleName(Integer id, String roleName);

    boolean checkRoleCode(Integer id, String roleCode);

    Boolean delete(Integer id);

    Boolean disable(Integer id, Integer status);
}
