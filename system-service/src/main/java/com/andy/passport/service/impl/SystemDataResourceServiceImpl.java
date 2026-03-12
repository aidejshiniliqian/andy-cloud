package com.andy.passport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemDataResourceEditDto;
import com.andy.passport.dto.SystemDataResourceQueryDto;
import com.andy.passport.entity.SystemDataResource;
import com.andy.passport.mapper.SystemDataResourceMapper;
import com.andy.passport.service.SystemDataResourceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * 数据资源表 服务实现类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Service
public class SystemDataResourceServiceImpl extends ServiceImpl<SystemDataResourceMapper, SystemDataResource> implements SystemDataResourceService {

    /**
     * 分页查询数据资源表
     * @param page
     * @param systemDataResourceQueryDto
     * @return
     */
    @Override
    public IPage<SystemDataResource> page(Page<SystemDataResource> page, SystemDataResourceQueryDto systemDataResourceQueryDto) {
        LambdaQueryWrapper<SystemDataResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(systemDataResourceQueryDto.getDataName()), SystemDataResource::getDataName, systemDataResourceQueryDto.getDataName())
                        .in(SystemDataResource::getStatus, Arrays.asList(BaseEntityConstants.Ok_Disable))
                        .orderByAsc(SystemDataResource::getSort);
        return super.page(page, queryWrapper);
    }

    /**
     * 分页查询有效数据资源表(status=0)
     * @param page
     * @param systemDataResourceQueryDto
     * @return
     */
    @Override
    public IPage<SystemDataResource> pageEffective(Page<SystemDataResource> page, SystemDataResourceQueryDto systemDataResourceQueryDto) {
        LambdaQueryWrapper<SystemDataResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(systemDataResourceQueryDto.getDataName()), SystemDataResource::getDataName, systemDataResourceQueryDto.getDataName())
                        .eq(SystemDataResource::getStatus, BaseEntityConstants.Status_OK)
                        .orderByAsc(SystemDataResource::getSort);
        return super.page(page, queryWrapper);
    }

    /**
     * 查询单个数据资源
     * @param id
     * @return
     */
    @Override
    public SystemDataResource queryInfo(Integer id){
        SystemDataResource systemDataResource = this.getById(id);
        if(null == systemDataResource){
            throw new BusinessException("数据错误");
        }
        return systemDataResource;
    }

    /**
     * 新增或编辑数据资源
     * @param systemDataResourceEditDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer edit(SystemDataResourceEditDto systemDataResourceEditDto){
        if(this.checkDataName(systemDataResourceEditDto.getId(), systemDataResourceEditDto.getDataName())){
            throw new BusinessException("数据资源名称不能重复");
        }
        SystemDataResource systemDataResource;
        if(null != systemDataResourceEditDto.getId()){
            systemDataResource = this.getById(systemDataResourceEditDto.getId());
            if(null == systemDataResource){
                throw new BusinessException("数据错误");
            }
            BeanUtil.copyProperties(systemDataResourceEditDto, systemDataResource);
//            systemDataResource.setUpdateBy();
            systemDataResource.setUpdateTime(new Date());
        }else {
            systemDataResource = new SystemDataResource();
            BeanUtil.copyProperties(systemDataResourceEditDto, systemDataResource);
//            systemDataResource.setCreateBy();
            systemDataResource.setStatus(BaseEntityConstants.Status_OK);
            systemDataResource.setCreateTime(new Date());
        }
        super.saveOrUpdate(systemDataResource);
        return systemDataResource.getId();
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {
        SystemDataResource systemDataResource = this.getById(id);
        if(null == systemDataResource){
            throw new BusinessException("数据错误");
        }
        systemDataResource.setStatus(BaseEntityConstants.Status_Remove);
        systemDataResource.setUpdateTime(new Date());
        return super.updateById(systemDataResource);
    }

    /**
     * 启用/禁用数据资源
     * @param id
     * @param status
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean disable(Integer id, Integer status) {
        SystemDataResource systemDataResource = this.getById(id);
        if(null == systemDataResource){
            throw new BusinessException("数据错误");
        }
        if(1 != status && 0 != status){
            throw new BusinessException("状态错误");
        }
        systemDataResource.setStatus(status);
        systemDataResource.setUpdateTime(new Date());
        return super.updateById(systemDataResource);
    }

    /**
     * 判断数据资源是否存在，true==存在，false==不存在
     * @param id
     * @param dataName
     * @return
     */
    @Override
    public Boolean checkDataName(Integer id, String dataName){
        LambdaQueryWrapper<SystemDataResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemDataResource::getDataName, dataName)
                .ne(null != id, SystemDataResource::getId, id)
                .eq(SystemDataResource::getStatus, BaseEntityConstants.Status_OK);
        return this.count(queryWrapper) > 0;
    }

}
