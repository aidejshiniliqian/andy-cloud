package com.andy.passport.service;

import com.andy.passport.dto.SystemDataUserRuleBindDto;
import com.andy.passport.entity.SystemDataUserRule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SystemDataUserRuleService extends IService<SystemDataUserRule> {

    List<SystemDataUserRule> getByUserId(Integer userId);

    Boolean bindUserDataRules(SystemDataUserRuleBindDto dto);
}
