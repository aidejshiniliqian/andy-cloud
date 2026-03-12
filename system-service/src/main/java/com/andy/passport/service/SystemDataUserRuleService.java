package com.andy.passport.service;

import com.andy.passport.dto.SystemDataUserRuleEditDto;
import com.andy.passport.dto.SystemDataUserRuleQueryDto;
import com.andy.passport.entity.SystemDataUserRule;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户数据权限规则表 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemDataUserRuleService extends IService<SystemDataUserRule> {

    IPage<SystemDataUserRule> page(Page<SystemDataUserRule> page, SystemDataUserRuleQueryDto systemDataUserRuleQueryDto);

    SystemDataUserRule queryInfo(Integer id);

    Integer edit(SystemDataUserRuleEditDto systemDataUserRuleEditDto);

    Boolean delete(Integer id);

    Boolean disable(Integer id, Integer status);

}
