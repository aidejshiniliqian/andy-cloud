package com.andy.passport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemDataUserRuleEditDto;
import com.andy.passport.dto.SystemDataUserRuleQueryDto;
import com.andy.passport.entity.SystemDataUserRule;
import com.andy.passport.mapper.SystemDataUserRuleMapper;
import com.andy.passport.service.SystemDataUserRuleService;
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
 * 用户数据权限规则表 服务实现类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Service
public class SystemDataUserRuleServiceImpl extends ServiceImpl<SystemDataUserRuleMapper, SystemDataUserRule> implements SystemDataUserRuleService {

    /**
     * 分页查询用户数据权限规则表
     * @param page
     * @param systemDataUserRuleQueryDto
     * @return
     */
    @Override
    public IPage<SystemDataUserRule> page(Page<SystemDataUserRule> page, SystemDataUserRuleQueryDto systemDataUserRuleQueryDto) {
        LambdaQueryWrapper<SystemDataUserRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != systemDataUserRuleQueryDto.getUserId(), SystemDataUserRule::getUserId, systemDataUserRuleQueryDto.getUserId())
                .eq(null != systemDataUserRuleQueryDto.getDataId(), SystemDataUserRule::getDataId, systemDataUserRuleQueryDto.getDataId())
                .in(SystemDataUserRule::getStatus, Arrays.asList(BaseEntityConstants.Ok_Disable))
                .orderByDesc(SystemDataUserRule::getCreateTime);
        return super.page(page, queryWrapper);
    }

    /**
     * 查询单个用户数据权限规则
     * @param id
     * @return
     */
    @Override
    public SystemDataUserRule queryInfo(Integer id){
        SystemDataUserRule systemDataUserRule = this.getById(id);
        if(null == systemDataUserRule){
            throw new BusinessException("数据错误");
        }
        return systemDataUserRule;
    }

    /**
     * 新增或编辑用户数据权限规则
     * @param systemDataUserRuleEditDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer edit(SystemDataUserRuleEditDto systemDataUserRuleEditDto){
        SystemDataUserRule systemDataUserRule;
        if(null != systemDataUserRuleEditDto.getId()){
            systemDataUserRule = this.getById(systemDataUserRuleEditDto.getId());
            if(null == systemDataUserRule){
                throw new BusinessException("数据错误");
            }
            BeanUtil.copyProperties(systemDataUserRuleEditDto, systemDataUserRule);
            systemDataUserRule.setUpdateTime(new Date());
        }else {
            systemDataUserRule = new SystemDataUserRule();
            BeanUtil.copyProperties(systemDataUserRuleEditDto, systemDataUserRule);
            systemDataUserRule.setStatus(BaseEntityConstants.Status_OK);
            systemDataUserRule.setCreateTime(new Date());
        }
        super.saveOrUpdate(systemDataUserRule);
        return systemDataUserRule.getId();
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {
        SystemDataUserRule systemDataUserRule = this.getById(id);
        if(null == systemDataUserRule){
            throw new BusinessException("数据错误");
        }
        systemDataUserRule.setStatus(BaseEntityConstants.Status_Remove);
        systemDataUserRule.setUpdateTime(new Date());
        return super.updateById(systemDataUserRule);
    }

    /**
     * 启用/禁用用户数据权限规则
     * @param id
     * @param status
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean disable(Integer id, Integer status) {
        SystemDataUserRule systemDataUserRule = this.getById(id);
        if(null == systemDataUserRule){
            throw new BusinessException("数据错误");
        }
        if(1 != status && 0 != status){
            throw new BusinessException("状态错误");
        }
        systemDataUserRule.setStatus(status);
        systemDataUserRule.setUpdateTime(new Date());
        return super.updateById(systemDataUserRule);
    }

}
