package com.andy.passport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemUserEditDto;
import com.andy.passport.dto.SystemUserQueryDto;
import com.andy.passport.entity.SystemUser;
import com.andy.passport.mapper.SystemUserMapper;
import com.andy.passport.service.SystemUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    /**
     * 分页查询用户
     * @param page
     * @param systemUserQueryDto
     * @return
     */
    @Override
    public IPage<SystemUser> page(Page<SystemUser> page, SystemUserQueryDto systemUserQueryDto) {
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(systemUserQueryDto.getUserName()), SystemUser::getUserName, systemUserQueryDto.getUserName())
                .like(StringUtils.isNotBlank(systemUserQueryDto.getUserCode()), SystemUser::getUserCode, systemUserQueryDto.getUserCode())
                .like(StringUtils.isNotBlank(systemUserQueryDto.getMobile()), SystemUser::getMobile, systemUserQueryDto.getMobile())
                .like(StringUtils.isNotBlank(systemUserQueryDto.getNumber()), SystemUser::getNumber, systemUserQueryDto.getNumber())
                .in(SystemUser::getStatus, Arrays.asList(BaseEntityConstants.Ok_Disable))
                .orderByAsc(SystemUser::getSort);
        return super.page(page, queryWrapper);
    }

    /**
     * 新增或编辑用户
     * @param systemUserEditDto
     * @return
     */
    @Override
    public Integer editUser(SystemUserEditDto systemUserEditDto) {
        SystemUser systemUser;
        if(this.checkUserCode(systemUserEditDto.getId(), systemUserEditDto.getUserCode())){
            throw new BusinessException("用户账号不能重复");
        }
        if(this.checkUserMobile(systemUserEditDto.getId(), systemUserEditDto.getMobile())){
            throw new BusinessException("用户手机号不能重复");
        }
        if(this.checkUserNumber(systemUserEditDto.getId(), systemUserEditDto.getNumber())){
            throw new BusinessException("员工编号不能重复");
        }
        if(null == systemUserEditDto.getId()){
            systemUser = new SystemUser();
            BeanUtil.copyProperties(systemUserEditDto, systemUser);
//            systemUser.setCreateBy();
            systemUser.setCreateTime(new Date());
            systemUser.setStatus(BaseEntityConstants.Status_OK);
        }else {
            systemUser = super.getById(systemUserEditDto.getId());
            BeanUtil.copyProperties(systemUserEditDto, systemUser);
            systemUser.setUpdateTime(new Date());
//            systemUser.setUpdateBy();
        }
        super.saveOrUpdate(systemUser);
        return systemUser.getId();
    }

    /**
     * 用户账号不能重复
     * @param id
     * @param userCode
     * @return
     */
    @Override
    public Boolean checkUserCode(Integer id, String userCode) {
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getUserCode, userCode)
                .ne(null != id, SystemUser::getId, id)
                .in(SystemUser::getStatus, Arrays.asList(BaseEntityConstants.Ok_Disable));
        return super.count(queryWrapper) > 0;
    }

    /**
     * 员工编号不能重复
     * @param id
     * @param number
     * @return
     */
    @Override
    public Boolean checkUserNumber(Integer id, String number) {
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getNumber, number)
                .ne(null != id, SystemUser::getId, id)
                .in(SystemUser::getStatus, Arrays.asList(BaseEntityConstants.Ok_Disable));
        return super.count(queryWrapper) > 0;
    }

    /**
     * 用户手机号不能重复
     * @param id
     * @param mobile
     * @return
     */
    @Override
    public Boolean checkUserMobile(Integer id, String mobile) {
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getMobile, mobile)
                .ne(null != id, SystemUser::getId, id)
                .in(SystemUser::getStatus, Arrays.asList(BaseEntityConstants.Ok_Disable));
        return super.count(queryWrapper) > 0;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Integer id) {
        SystemUser systemUser = super.getById(id);
        if(null == systemUser){
            throw new BusinessException("数据错误");
        }
        systemUser.setStatus(BaseEntityConstants.Status_Remove);
//        systemUser.setUpdateBy();
        systemUser.setUpdateTime(new Date());
        return super.updateById(systemUser);
    }

    /**
     * 禁用/启用用户
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean disable(Integer id, Integer status) {
        SystemUser systemUser = super.getById(id);
        if(null == systemUser){
            throw new BusinessException("数据错误");
        }
        if(!BaseEntityConstants.Status_Disable.equals(status) && !BaseEntityConstants.Status_OK.equals(status)){
            throw new BusinessException("状态错误");
        }
        systemUser.setStatus(status);
//        systemUser.setUpdateBy();
        systemUser.setUpdateTime(new Date());
        return super.updateById(systemUser);
    }

    /**
     * 根据用户账号查询用户信息
     * @param userCode 用户账号
     * @return 用户信息
     */
    @Override
    public SystemUser getByUserCode(String userCode) {
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getUserCode, userCode)
                .in(SystemUser::getStatus, Arrays.asList(BaseEntityConstants.Ok_Disable));
        return super.getOne(queryWrapper);
    }

}
