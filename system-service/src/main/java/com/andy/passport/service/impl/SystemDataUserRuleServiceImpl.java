package com.andy.passport.service.impl;

import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemDataUserRuleEditDto;
import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.entity.SystemDataUserRule;
import com.andy.passport.entity.SystemUserRole;
import com.andy.passport.mapper.SystemDataUserRuleMapper;
import com.andy.passport.service.SystemDataRoleRuleService;
import com.andy.passport.service.SystemDataUserRuleService;
import com.andy.passport.service.SystemUserRoleService;
import com.andy.passport.vo.SystemDataRoleRuleVo;
import com.andy.passport.vo.SystemDataUserFinalRuleVo;
import com.andy.passport.vo.SystemDataUserRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Resource
    private SystemUserRoleService systemUserRoleService;

    @Resource
    private SystemDataRoleRuleService systemDataRoleRuleService;

    /**
     * 查询用户下的数据权限列表
     * @param userId
     * @return
     */
    @Override
    public List<SystemDataUserRuleVo> queryList(Integer userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        return baseMapper.queryList(userId);
    }

    /**
     * 新增或编辑用户数据权限规则（批量授权）
     * @param systemDataUserRuleEditDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SystemDataUserRuleEditDto systemDataUserRuleEditDto) {
        if (systemDataUserRuleEditDto.getUserId() == null) {
            throw new BusinessException("用户ID不能为空");
        }
        if (CollectionUtils.isEmpty(systemDataUserRuleEditDto.getDataIds())) {
            throw new BusinessException("资源ID列表不能为空");
        }

        // 先删除该用户下所有现有的数据权限规则（逻辑删除）
        LambdaQueryWrapper<SystemDataUserRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemDataUserRule::getUserId, systemDataUserRuleEditDto.getUserId())
               .in(SystemDataUserRule::getStatus, BaseEntityConstants.Ok_Disable);
        List<SystemDataUserRule> existingRules = this.list(wrapper);

        for (SystemDataUserRule rule : existingRules) {
            rule.setStatus(BaseEntityConstants.Status_Remove);
            rule.setUpdateTime(new Date());
        }
        if (!existingRules.isEmpty()) {
            this.updateBatchById(existingRules);
        }

        // 批量新增新的数据权限规则
        for (Integer dataId : systemDataUserRuleEditDto.getDataIds()) {
            SystemDataUserRule rule = new SystemDataUserRule();
            rule.setUserId(systemDataUserRuleEditDto.getUserId());
            rule.setDataId(dataId);
            rule.setRuleType(systemDataUserRuleEditDto.getRuleType());
            rule.setStatus(BaseEntityConstants.Status_OK);
            rule.setCreateTime(new Date());
            this.save(rule);
        }

        return true;
    }

    /**
     * 查询用户最终的数据权限列表（用户数据权限和角色数据权限的并集）
     * @param userId
     * @return
     */
    @Override
    public List<SystemDataUserFinalRuleVo> queryFinalList(Integer userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }

        List<SystemDataUserFinalRuleVo> finalList = new ArrayList<>();

        // 1. 查询用户直接授权的数据权限
        List<SystemDataUserRuleVo> userRules = this.queryList(userId);
        for (SystemDataUserRuleVo userRule : userRules) {
            SystemDataUserFinalRuleVo finalVo = new SystemDataUserFinalRuleVo();
            finalVo.setId(userRule.getId());
            finalVo.setDataId(userRule.getDataId());
            finalVo.setRuleType(userRule.getRuleType());
            finalVo.setSourceType("USER");
            finalVo.setDataName(userRule.getDataName());
            finalVo.setDataTable(userRule.getDataTable());
            finalList.add(finalVo);
        }

        // 2. 查询用户所属角色的数据权限
        List<SystemUserRole> userRoles = systemUserRoleService.list(
                new LambdaQueryWrapper<SystemUserRole>()
                        .eq(SystemUserRole::getUserId, userId)
        );

        for (SystemUserRole userRole : userRoles) {
            List<SystemDataRoleRuleVo> roleRules = systemDataRoleRuleService.queryList(
                    new com.andy.passport.dto.SystemDataRoleRuleQueryDto() {{
                        setRoleId(userRole.getRoleId());
                    }}
            );

            for (SystemDataRoleRuleVo roleRule : roleRules) {
                // 检查是否已存在相同资源ID的数据权限
                boolean exists = finalList.stream()
                        .anyMatch(item -> item.getDataId().equals(roleRule.getDataId()));

                if (!exists) {
                    SystemDataUserFinalRuleVo finalVo = new SystemDataUserFinalRuleVo();
                    finalVo.setId(roleRule.getId());
                    finalVo.setDataId(roleRule.getDataId());
                    finalVo.setRuleType(roleRule.getRuleType());
                    finalVo.setSourceType("ROLE");
                    finalVo.setDataName(roleRule.getDataName());
                    finalVo.setDataTable(roleRule.getDataTable());
                    finalList.add(finalVo);
                }
            }
        }

        return finalList;
    }

}
