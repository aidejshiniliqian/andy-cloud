package com.andy.passport.service;

import com.andy.passport.dto.SystemDataRoleRuleBindDto;
import com.andy.passport.entity.SystemDataRoleRule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色数据权限规则表 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemDataRoleRuleService extends IService<SystemDataRoleRule> {

    /**
     * 查询角色数据权限列表
     * @param roleId 角色ID
     * @return 数据权限列表
     */
    List<SystemDataRoleRule> getRoleDataRules(Integer roleId);

    /**
     * 绑定角色数据权限
     * @param dto 角色数据权限绑定DTO
     * @return 是否成功
     */
    Boolean bindRoleDataRules(SystemDataRoleRuleBindDto dto);

}
