package com.andy.passport.service;

import com.andy.passport.dto.SystemDataRoleRuleEditDto;
import com.andy.passport.dto.SystemDataRoleRuleQueryDto;
import com.andy.passport.entity.SystemDataRoleRule;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色数据权限规则表 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemDataRoleRuleService extends IService<SystemDataRoleRule> {

    IPage<SystemDataRoleRule> page(Page<SystemDataRoleRule> page, SystemDataRoleRuleQueryDto systemDataRoleRuleQueryDto);

    SystemDataRoleRule queryInfo(Integer id);

    Integer edit(SystemDataRoleRuleEditDto systemDataRoleRuleEditDto);

    Boolean delete(Integer id);

    Boolean disable(Integer id, Integer status);

}
