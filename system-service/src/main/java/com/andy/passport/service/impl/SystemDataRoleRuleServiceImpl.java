package com.andy.passport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemDataRoleRuleEditDto;
import com.andy.passport.dto.SystemDataRoleRuleQueryDto;
import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.mapper.SystemDataRoleRuleMapper;
import com.andy.passport.service.SystemDataRoleRuleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

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
     * 分页查询角色数据权限规则表
     * @param page
     * @param systemDataRoleRuleQueryDto
     * @return
     */
    @Override
    public IPage<SystemDataRoleRule> page(Page<SystemDataRoleRule> page, SystemDataRoleRuleQueryDto systemDataRoleRuleQueryDto) {
        LambdaQueryWrapper<SystemDataRoleRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != systemDataRoleRuleQueryDto.getRoleId(), SystemDataRoleRule::getRoleId, systemDataRoleRuleQueryDto.getRoleId())
                .eq(null != systemDataRoleRuleQueryDto.getDataId(), SystemDataRoleRule::getDataId, systemDataRoleRuleQueryDto.getDataId())
                .in(SystemDataRoleRule::getStatus, Arrays.asList(BaseEntityConstants.Ok_Disable))
                .orderByDesc(SystemDataRoleRule::getCreateTime);
        return super.page(page, queryWrapper);
    }

    /**
     * 查询单个角色数据权限规则
     * @param id
     * @return
     */
    @Override
    public SystemDataRoleRule queryInfo(Integer id){
        SystemDataRoleRule systemDataRoleRule = this.getById(id);
        if(null == systemDataRoleRule){
            throw new BusinessException("数据错误");
        }
        return systemDataRoleRule;
    }

    /**
     * 新增或编辑角色数据权限规则
     * @param systemDataRoleRuleEditDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer edit(SystemDataRoleRuleEditDto systemDataRoleRuleEditDto){
        SystemDataRoleRule systemDataRoleRule;
        if(null != systemDataRoleRuleEditDto.getId()){
            systemDataRoleRule = this.getById(systemDataRoleRuleEditDto.getId());
            if(null == systemDataRoleRule){
                throw new BusinessException("数据错误");
            }
            BeanUtil.copyProperties(systemDataRoleRuleEditDto, systemDataRoleRule);
            systemDataRoleRule.setUpdateTime(new Date());
        }else {
            systemDataRoleRule = new SystemDataRoleRule();
            BeanUtil.copyProperties(systemDataRoleRuleEditDto, systemDataRoleRule);
            systemDataRoleRule.setStatus(BaseEntityConstants.Status_OK);
            systemDataRoleRule.setCreateTime(new Date());
        }
        super.saveOrUpdate(systemDataRoleRule);
        return systemDataRoleRule.getId();
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {
        SystemDataRoleRule systemDataRoleRule = this.getById(id);
        if(null == systemDataRoleRule){
            throw new BusinessException("数据错误");
        }
        systemDataRoleRule.setStatus(BaseEntityConstants.Status_Remove);
        systemDataRoleRule.setUpdateTime(new Date());
        return super.updateById(systemDataRoleRule);
    }

    /**
     * 启用/禁用角色数据权限规则
     * @param id
     * @param status
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean disable(Integer id, Integer status) {
        SystemDataRoleRule systemDataRoleRule = this.getById(id);
        if(null == systemDataRoleRule){
            throw new BusinessException("数据错误");
        }
        if(1 != status && 0 != status){
            throw new BusinessException("状态错误");
        }
        systemDataRoleRule.setStatus(status);
        systemDataRoleRule.setUpdateTime(new Date());
        return super.updateById(systemDataRoleRule);
    }

}
