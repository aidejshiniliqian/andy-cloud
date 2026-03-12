package com.andy.passport.service.impl;

import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemDataUserRuleBindDto;
import com.andy.passport.entity.SystemDataResource;
import com.andy.passport.entity.SystemDataUserRule;
import com.andy.passport.entity.SystemUser;
import com.andy.passport.mapper.SystemDataUserRuleMapper;
import com.andy.passport.service.SystemDataResourceService;
import com.andy.passport.service.SystemDataUserRuleService;
import com.andy.passport.service.SystemUserService;
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
public class SystemDataUserRuleServiceImpl extends ServiceImpl<SystemDataUserRuleMapper, SystemDataUserRule> implements SystemDataUserRuleService {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemDataResourceService systemDataResourceService;

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
}
