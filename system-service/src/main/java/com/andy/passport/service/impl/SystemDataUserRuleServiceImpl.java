package com.andy.passport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemDataUserRuleBindDto;
import com.andy.passport.entity.SystemDataResource;
import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.entity.SystemDataUserRule;
import com.andy.passport.entity.SystemRole;
import com.andy.passport.entity.SystemUser;
import com.andy.passport.mapper.SystemDataUserRuleMapper;
import com.andy.passport.service.SystemDataResourceService;
import com.andy.passport.service.SystemDataRoleRuleService;
import com.andy.passport.service.SystemDataUserRuleService;
import com.andy.passport.service.SystemUserRoleService;
import com.andy.passport.service.SystemUserService;
import com.andy.passport.vo.SystemDataUserRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SystemDataUserRuleServiceImpl extends ServiceImpl<SystemDataUserRuleMapper, SystemDataUserRule> implements SystemDataUserRuleService {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemDataResourceService systemDataResourceService;

    @Autowired
    private SystemUserRoleService systemUserRoleService;

    @Autowired
    private SystemDataRoleRuleService systemDataRoleRuleService;

    @Override
    public List<SystemDataUserRule> getByUserId(Integer userId) {
        LambdaQueryWrapper<SystemDataUserRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemDataUserRule::getUserId, userId);
        return super.list(queryWrapper);
    }

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
            List<Integer> dataIds = dto.getDataRules().stream()
                    .map(SystemDataUserRuleBindDto.DataRuleItem::getDataId)
                    .distinct()
                    .collect(Collectors.toList());

            List<SystemDataResource> resources = systemDataResourceService.listByIds(dataIds);
            if (resources.size() != dataIds.size()) {
                throw new BusinessException("数据资源不存在");
            }

            List<String> validRuleTypes = Arrays.asList("ALL", "CHILDREN", "SELF");
            for (SystemDataUserRuleBindDto.DataRuleItem item : dto.getDataRules()) {
                if (!validRuleTypes.contains(item.getRuleType())) {
                    throw new BusinessException("数据权限规则类型错误");
                }
            }

            List<SystemDataUserRule> userRules = dto.getDataRules().stream()
                    .map(item -> {
                        SystemDataUserRule userRule = new SystemDataUserRule();
                        userRule.setUserId(dto.getUserId());
                        userRule.setDataId(item.getDataId());
                        userRule.setRuleType(item.getRuleType());
                        userRule.setCreateTime(new Date());
                        return userRule;
                    })
                    .collect(Collectors.toList());
            super.saveBatch(userRules);
        }
        return true;
    }

    @Override
    public List<SystemDataUserRuleVo> getFinalDataRulesByUserId(Integer userId) {
        SystemUser user = systemUserService.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        List<SystemDataUserRule> userRules = getByUserId(userId);

        List<SystemRole> roles = systemUserRoleService.getUserRoles(userId);
        List<SystemDataRoleRule> roleRules = new ArrayList<>();
        if (roles != null && !roles.isEmpty()) {
            List<Integer> roleIds = roles.stream()
                    .map(SystemRole::getId)
                    .toList();
            for (Integer roleId : roleIds) {
                List<SystemDataRoleRule> rules = systemDataRoleRuleService.getByRoleId(roleId);
                roleRules.addAll(rules);
            }
        }

        Map<Integer, String> mergedRules = new HashMap<>();

        for (SystemDataUserRule rule : userRules) {
            mergedRules.put(rule.getDataId(), rule.getRuleType());
        }

        for (SystemDataRoleRule rule : roleRules) {
            Integer dataId = rule.getDataId();
            String existingRule = mergedRules.get(dataId);
            if (existingRule == null) {
                mergedRules.put(dataId, rule.getRuleType());
            } else {
                String mergedRuleType = mergeRuleType(existingRule, rule.getRuleType());
                mergedRules.put(dataId, mergedRuleType);
            }
        }

        if (mergedRules.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> dataIds = new ArrayList<>(mergedRules.keySet());
        List<SystemDataResource> resources = systemDataResourceService.listByIds(dataIds);
        Map<Integer, SystemDataResource> resourceMap = resources.stream()
                .collect(Collectors.toMap(SystemDataResource::getId, r -> r));

        List<SystemDataUserRuleVo> result = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : mergedRules.entrySet()) {
            SystemDataUserRuleVo vo = new SystemDataUserRuleVo();
            vo.setUserId(userId);
            vo.setDataId(entry.getKey());
            vo.setRuleType(entry.getValue());
            SystemDataResource resource = resourceMap.get(entry.getKey());
            if (resource != null) {
                vo.setDataName(resource.getDataName());
                vo.setDataTable(resource.getDataTable());
            }
            result.add(vo);
        }

        return result;
    }

    private String mergeRuleType(String rule1, String rule2) {
        if ("ALL".equals(rule1) || "ALL".equals(rule2)) {
            return "ALL";
        }
        if ("CHILDREN".equals(rule1) || "CHILDREN".equals(rule2)) {
            return "CHILDREN";
        }
        return "SELF";
    }
}
