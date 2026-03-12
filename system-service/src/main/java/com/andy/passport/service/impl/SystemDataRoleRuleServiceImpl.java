package com.andy.passport.service.impl;

import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemDataRoleRuleBindDto;
import com.andy.passport.entity.SystemDataResource;
import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.entity.SystemRole;
import com.andy.passport.mapper.SystemDataRoleRuleMapper;
import com.andy.passport.service.SystemDataResourceService;
import com.andy.passport.service.SystemDataRoleRuleService;
import com.andy.passport.service.SystemRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemDataRoleRuleServiceImpl extends ServiceImpl<SystemDataRoleRuleMapper, SystemDataRoleRule> implements SystemDataRoleRuleService {

    @Autowired
    private SystemRoleService systemRoleService;

    @Autowired
    private SystemDataResourceService systemDataResourceService;

    @Override
    public List<SystemDataRoleRule> getByRoleId(Integer roleId) {
        LambdaQueryWrapper<SystemDataRoleRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemDataRoleRule::getRoleId, roleId);
        return super.list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean bindRoleDataRules(SystemDataRoleRuleBindDto dto) {
        SystemRole role = systemRoleService.getById(dto.getRoleId());
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        LambdaQueryWrapper<SystemDataRoleRule> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SystemDataRoleRule::getRoleId, dto.getRoleId());
        super.remove(deleteWrapper);

        if (dto.getDataRules() != null && !dto.getDataRules().isEmpty()) {
            List<Integer> dataIds = dto.getDataRules().stream()
                    .map(SystemDataRoleRuleBindDto.DataRuleItem::getDataId)
                    .distinct()
                    .collect(Collectors.toList());

            List<SystemDataResource> resources = systemDataResourceService.listByIds(dataIds);
            if (resources.size() != dataIds.size()) {
                throw new BusinessException("数据资源不存在");
            }

            List<String> validRuleTypes = Arrays.asList("ALL", "CHILDREN", "SELF");
            for (SystemDataRoleRuleBindDto.DataRuleItem item : dto.getDataRules()) {
                if (!validRuleTypes.contains(item.getRuleType())) {
                    throw new BusinessException("数据权限规则类型错误");
                }
            }

            List<SystemDataRoleRule> roleRules = dto.getDataRules().stream()
                    .map(item -> {
                        SystemDataRoleRule roleRule = new SystemDataRoleRule();
                        roleRule.setRoleId(dto.getRoleId());
                        roleRule.setDataId(item.getDataId());
                        roleRule.setRuleType(item.getRuleType());
                        roleRule.setCreateTime(new Date());
                        return roleRule;
                    })
                    .collect(Collectors.toList());
            super.saveBatch(roleRules);
        }
        return true;
    }
}
