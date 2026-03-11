package com.andy.passport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemSubSystemEditDto;
import com.andy.passport.dto.SystemSubSystemQueryDto;
import com.andy.passport.entity.SystemSubSystem;
import com.andy.passport.mapper.SystemSubSystemMapper;
import com.andy.passport.service.SystemSubSystemService;
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
 * 子系统 服务实现类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-03-03
 */
@Service
public class SystemSubSystemServiceImpl extends ServiceImpl<SystemSubSystemMapper, SystemSubSystem> implements SystemSubSystemService {

    /**
     * 分页查询子系统
     * @param page
     * @param systemSubSystemQueryDto
     * @return
     */
    @Override
    public IPage<SystemSubSystem> queryPage(Page<SystemSubSystem> page, SystemSubSystemQueryDto systemSubSystemQueryDto) {
        LambdaQueryWrapper<SystemSubSystem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(systemSubSystemQueryDto.getSubSystemCode()), SystemSubSystem::getSubSystemCode, systemSubSystemQueryDto.getSubSystemCode())
                .like(StringUtils.isNotBlank(systemSubSystemQueryDto.getSubSystemName()), SystemSubSystem::getSubSystemName, systemSubSystemQueryDto.getSubSystemName())
                .in(SystemSubSystem::getStatus, Arrays.asList(BaseEntityConstants.Ok_Disable))
                .orderByAsc(SystemSubSystem::getSort);
        return super.page(page, queryWrapper);
    }

    /**
     * 根据code查询子系统
     * @param subSystemCode
     * @return
     */
    @Override
    public SystemSubSystem queryBySubSystemCode(String subSystemCode) {
        LambdaQueryWrapper<SystemSubSystem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemSubSystem::getSubSystemCode, subSystemCode);
        SystemSubSystem systemSubSystem = super.getOne(queryWrapper);
        if (null == systemSubSystem) {
            throw new BusinessException("数据错误");
        }
        return systemSubSystem;
    }

    /**
     * 查询单个子系统
     * @param id
     * @return
     */
    @Override
    public SystemSubSystem queryInfo(Integer id) {
        SystemSubSystem systemSubSystem = super.getById(id);
        if (null == systemSubSystem) {
            throw new BusinessException("数据错误");
        }
        return systemSubSystem;
    }

    /**
     * 新增或编辑
     * @param systemSubSystemEditDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer edit(SystemSubSystemEditDto systemSubSystemEditDto) {
        if(this.checkSystemCode(systemSubSystemEditDto.getId(), systemSubSystemEditDto.getSubSystemCode())){
            throw new BusinessException("子系统编号不允许重复");
        }
        if(this.checkSystemName(systemSubSystemEditDto.getId(), systemSubSystemEditDto.getSubSystemName())){
            throw new BusinessException("子系统名称不允许重复");
        }
        SystemSubSystem systemSubSystem;
        if(null != systemSubSystemEditDto.getId()){
            systemSubSystem = super.getById(systemSubSystemEditDto.getId());
            BeanUtil.copyProperties(systemSubSystemEditDto, systemSubSystem);
//            systemSubSystem.setUpdateBy();
            systemSubSystem.setUpdateTime(new Date());
        }else {
            systemSubSystem = new SystemSubSystem();
            BeanUtil.copyProperties(systemSubSystemEditDto, systemSubSystem);
//            systemSubSystem.setCreateBy();
            systemSubSystem.setCreateTime(new Date());
        }
        super.saveOrUpdate(systemSubSystem);
        return systemSubSystem.getId();
    }

    /**
     * 启用/禁用
     * @param id
     * @param status
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean disable(Integer id, Integer status){
        SystemSubSystem systemSubSystem = this.getById(id);
        if(null == systemSubSystem){
            throw new BusinessException("数据错误");
        }
        if(1 != status && 0 != status){
            throw new BusinessException("状态错误");
        }
        systemSubSystem.setStatus(status);
        systemSubSystem.setUpdateTime(new Date());
        return super.updateById(systemSubSystem);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id){
        SystemSubSystem systemSubSystem = this.getById(id);
        if(null == systemSubSystem){
            throw new BusinessException("数据错误");
        }
        systemSubSystem.setStatus(BaseEntityConstants.Status_Remove);
        systemSubSystem.setUpdateTime(new Date());
        return super.updateById(systemSubSystem);
    }



    /**
     * 判断子系统名称是否存在，true==存在，false==不存在
     * @param id
     * @param systemName
     * @return
     */
    @Override
    public Boolean checkSystemName(Integer id, String systemName){
        LambdaQueryWrapper<SystemSubSystem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemSubSystem::getSubSystemName, systemName)
                .ne(null != id, SystemSubSystem::getId, id)
                .eq(SystemSubSystem::getStatus, BaseEntityConstants.Status_OK);
        return super.count(queryWrapper) > 0;
    }

    /**
     * 判断子系统编号是否存在，true==存在，false==不存在
     * @param id
     * @param systemCode
     * @return
     */
    @Override
    public Boolean checkSystemCode(Integer id, String systemCode){
        LambdaQueryWrapper<SystemSubSystem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemSubSystem::getSubSystemCode, systemCode)
                .ne(null != id, SystemSubSystem::getId, id)
                .eq(SystemSubSystem::getStatus, BaseEntityConstants.Status_OK);
        return super.count(queryWrapper) > 0;
    }
}
