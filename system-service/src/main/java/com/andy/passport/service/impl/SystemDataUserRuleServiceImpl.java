package com.andy.passport.service.impl;

import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemDataUserRuleBindDto;
import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.entity.SystemDataUserRule;
import com.andy.passport.entity.SystemRole;
import com.andy.passport.entity.SystemUser;
import com.andy.passport.mapper.SystemDataUserRuleMapper;
import com.andy.passport.service.SystemDataRoleRuleService;
import com.andy.passport.service.SystemDataUserRuleService;
import com.andy.passport.service.SystemUserRoleService;
import com.andy.passport.service.SystemUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户数据权限规则表 服务实现类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Service
public class SystemDataUserRuleServiceImpl extends ServiceImpl<SystemDataUserRuleMapper, SystemDataUserRule> implements SystemDataUserRuleService {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemUserRoleService systemUserRoleService;

    @Autowired
    private SystemDataRoleRuleService systemDataRoleRuleService;

    /**
     * 查询用户数据权限列表
     * @param userId 用户ID
     * @return 数据权限列表
     */
    @Override
    public List<SystemDataUserRule> getUserDataRules(Integer userId) {
        LambdaQueryWrapper<SystemDataUserRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemDataUserRule::getUserId, userId);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 绑定用户数据权限
     * @param dto 用户数据权限绑定DTO
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean bindUserDataRules(SystemDataUserRuleBindDto dto) {
        SystemUser user = systemUserService.getById(dto.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        LambdaQueryWrapper<SystemDataUserRule> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SystemDataUserRule::getUserId, dto.getUserId());
        super.remove(deleteWrapper);
        if (dto.getDataRules() != null && !dto.getDataRules().isEmpty()) {
            List<SystemDataUserRule> dataRules = dto.getDataRules().stream()
                    .map(item -> {
                        SystemDataUserRule dataRule = new SystemDataUserRule();
                        dataRule.setUserId(dto.getUserId());
                        dataRule.setDataId(item.getDataId());
                        dataRule.setRuleType(item.getRuleType());
                        dataRule.setCreateTime(new Date());
                        return dataRule;
                    })
                    .collect(Collectors.toList());
            super.saveBatch(dataRules);
        }
        return true;
    }

    @Override
    public List<SystemDataUserRule> getFinalUserDataRules(Integer userId) {
        List<SystemDataUserRule> userRules = getUserDataRules(userId);
        List<SystemRole> userRoles = systemUserRoleService.getUserRoles(userId);
        List<SystemDataRoleRule> roleRules = new ArrayList<>();
        for (SystemRole role : userRoles) {
            roleRules.addAll(systemDataRoleRuleService.getRoleDataRules(role.getId()));
        }
        Map<Integer, SystemDataUserRule> ruleMap = roleRules.stream()
                .map(roleRule -> {
                    SystemDataUserRule userRule = new SystemDataUserRule();
                    userRule.setUserId(userId);
                    userRule.setDataId(roleRule.getDataId());
                    userRule.setRuleType(roleRule.getRuleType());
                    return userRule;
                })
                .collect(Collectors.toMap(SystemDataUserRule::getDataId, Function.identity(), (oldVal, newVal) -> oldVal));
        for (SystemDataUserRule userRule : userRules) {
            ruleMap.put(userRule.getDataId(), userRule);
        }
        return new ArrayList<>(ruleMap.values());
    }

}
