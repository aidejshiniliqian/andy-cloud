package com.andy.passport.service;

import com.andy.passport.dto.SystemDataRoleRuleEditDto;
import com.andy.passport.dto.SystemDataRoleRuleQueryDto;
import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.vo.SystemDataRoleRuleVo;
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

    List<SystemDataRoleRuleVo> queryList(SystemDataRoleRuleQueryDto systemDataRoleRuleQueryDto);

    Boolean edit(SystemDataRoleRuleEditDto systemDataRoleRuleEditDto);

}
