package com.andy.passport.service.impl;

import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemDataRoleRuleEditDto;
import com.andy.passport.dto.SystemDataRoleRuleQueryDto;
import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.mapper.SystemDataRoleRuleMapper;
import com.andy.passport.service.SystemDataRoleRuleService;
import com.andy.passport.vo.SystemDataRoleRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色数据权限规则表 服务实现类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Service
public class SystemDataRoleRuleServiceImpl extends ServiceImpl<SystemDataRoleRuleMapper, SystemDataRoleRule> implements SystemDataRoleRuleService {

    /**
     * 查询角色下的数据权限列表
     * @param systemDataRoleRuleQueryDto
     * @return
     */
    @Override
    public List<SystemDataRoleRuleVo> queryList(SystemDataRoleRuleQueryDto systemDataRoleRuleQueryDto) {
        if (systemDataRoleRuleQueryDto.getRoleId() == null) {
            throw new BusinessException("角色ID不能为空");
        }
        return baseMapper.queryList(systemDataRoleRuleQueryDto.getRoleId());
    }

    /**
     * 新增或编辑角色数据权限规则（批量授权）
     * @param systemDataRoleRuleEditDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SystemDataRoleRuleEditDto systemDataRoleRuleEditDto) {
        if (systemDataRoleRuleEditDto.getRoleId() == null) {
            throw new BusinessException("角色ID不能为空");
        }
        if (CollectionUtils.isEmpty(systemDataRoleRuleEditDto.getDataIds())) {
            throw new BusinessException("资源ID列表不能为空");
        }

        // 先删除该角色下所有现有的数据权限规则（逻辑删除）
        LambdaQueryWrapper<SystemDataRoleRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemDataRoleRule::getRoleId, systemDataRoleRuleEditDto.getRoleId())
               .in(SystemDataRoleRule::getStatus, BaseEntityConstants.Ok_Disable);
        List<SystemDataRoleRule> existingRules = this.list(wrapper);

        for (SystemDataRoleRule rule : existingRules) {
            rule.setStatus(BaseEntityConstants.Status_Remove);
            rule.setUpdateTime(new Date());
        }
        if (!existingRules.isEmpty()) {
            this.updateBatchById(existingRules);
        }

        // 批量新增新的数据权限规则
        for (Integer dataId : systemDataRoleRuleEditDto.getDataIds()) {
            SystemDataRoleRule rule = new SystemDataRoleRule();
            rule.setRoleId(systemDataRoleRuleEditDto.getRoleId());
            rule.setDataId(dataId);
            rule.setRuleType(systemDataRoleRuleEditDto.getRuleType());
            rule.setStatus(BaseEntityConstants.Status_OK);
            rule.setCreateTime(new Date());
            this.save(rule);
        }

        return true;
    }

}
