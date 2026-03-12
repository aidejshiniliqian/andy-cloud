package com.andy.passport.service.impl;

import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemDataRoleRuleBindDto;
import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.entity.SystemRole;
import com.andy.passport.mapper.SystemDataRoleRuleMapper;
import com.andy.passport.service.SystemDataRoleRuleService;
import com.andy.passport.service.SystemRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 查询角色数据权限列表
     * @param roleId 角色ID
     * @return 数据权限列表
     */
    @Override
    public List<SystemDataRoleRule> getRoleDataRules(Integer roleId) {
        LambdaQueryWrapper<SystemDataRoleRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemDataRoleRule::getRoleId, roleId);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 绑定角色数据权限
     * @param dto 角色数据权限绑定DTO
     * @return 是否成功
     */
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
            List<SystemDataRoleRule> dataRules = dto.getDataRules().stream()
                    .map(item -> {
                        SystemDataRoleRule dataRule = new SystemDataRoleRule();
                        dataRule.setRoleId(dto.getRoleId());
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

}
