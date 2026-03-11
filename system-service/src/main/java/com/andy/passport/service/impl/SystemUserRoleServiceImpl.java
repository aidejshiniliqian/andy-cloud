package com.andy.passport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemUserRoleBindDto;
import com.andy.passport.entity.SystemRole;
import com.andy.passport.entity.SystemUser;
import com.andy.passport.entity.SystemUserRole;
import com.andy.passport.mapper.SystemUserRoleMapper;
import com.andy.passport.service.SystemUserRoleService;
import com.andy.passport.service.SystemUserService;
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
 * 用户角色表 服务实现类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Service
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleMapper, SystemUserRole> implements SystemUserRoleService {

    @Autowired
    private SystemUserService systemUserService;

    /**
     * 查询用户角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SystemRole> getUserRoles(Integer userId) {
        LambdaQueryWrapper<SystemUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUserRole::getUserId, userId);
        return baseMapper.selectRolesByUserId(queryWrapper);
    }

    /**
     * 绑定用户角色
     * @param dto 用户角色绑定DTO
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean bindUserRoles(SystemUserRoleBindDto dto) {
        // 检查用户是否存在
        SystemUser user = systemUserService.getById(dto.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 删除用户原有角色（物理删除）
        LambdaQueryWrapper<SystemUserRole> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SystemUserRole::getUserId, dto.getUserId());
        super.remove(deleteWrapper);
        // 批量插入新角色
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            List<SystemUserRole> userRoles = dto.getRoleIds().stream()
                    .map(roleId -> {
                        SystemUserRole userRole = new SystemUserRole();
                        userRole.setUserId(dto.getUserId());
                        userRole.setRoleId(roleId);
                        userRole.setCreateTime(new Date());
                        return userRole;
                    })
                    .collect(Collectors.toList());
            super.saveBatch(userRoles);
        }
        return true;
    }
}
