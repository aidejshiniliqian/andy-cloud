package com.andy.passport.service;

import com.andy.passport.dto.SystemDataRoleRuleBindDto;
import com.andy.passport.entity.SystemDataRoleRule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SystemDataRoleRuleService extends IService<SystemDataRoleRule> {

    List<SystemDataRoleRule> getByRoleId(Integer roleId);

    Boolean bindRoleDataRules(SystemDataRoleRuleBindDto dto);
}
