package com.andy.passport.service;

import com.andy.passport.dto.SystemDataUserRuleEditDto;
import com.andy.passport.dto.SystemDataUserRuleQueryDto;
import com.andy.passport.entity.SystemDataUserRule;
import com.andy.passport.vo.SystemDataUserFinalRuleVo;
import com.andy.passport.vo.SystemDataUserRuleVo;
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

    List<SystemDataUserRuleVo> queryList(Integer userId);

    Boolean edit(SystemDataUserRuleEditDto systemDataUserRuleEditDto);

    List<SystemDataUserFinalRuleVo> queryFinalList(Integer userId);

}
