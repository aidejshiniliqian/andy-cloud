package com.andy.passport.service;

import com.andy.passport.dto.SystemUserRoleBindDto;
import com.andy.passport.entity.SystemRole;
import com.andy.passport.entity.SystemUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemUserRoleService extends IService<SystemUserRole> {

    /**
     * 查询用户角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SystemRole> getUserRoles(Integer userId);

    /**
     * 绑定用户角色
     * @param dto 用户角色绑定DTO
     * @return 是否成功
     */
    Boolean bindUserRoles(SystemUserRoleBindDto dto);
}
