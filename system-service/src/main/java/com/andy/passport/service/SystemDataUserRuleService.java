package com.andy.passport.service;

import com.andy.passport.dto.SystemDataUserRuleBindDto;
import com.andy.passport.entity.SystemDataUserRule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户数据权限规则表 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemDataUserRuleService extends IService<SystemDataUserRule> {

    /**
     * 查询用户数据权限列表
     * @param userId 用户ID
     * @return 数据权限列表
     */
    List<SystemDataUserRule> getUserDataRules(Integer userId);

    /**
     * 绑定用户数据权限
     * @param dto 用户数据权限绑定DTO
     * @return 是否成功
     */
    Boolean bindUserDataRules(SystemDataUserRuleBindDto dto);

}
