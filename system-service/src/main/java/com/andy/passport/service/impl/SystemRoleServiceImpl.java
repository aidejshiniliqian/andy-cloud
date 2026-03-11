package com.andy.passport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemRoleEditDto;
import com.andy.passport.dto.SystemRoleQueryDto;
import com.andy.passport.entity.SystemRole;
import com.andy.passport.mapper.SystemRoleMapper;
import com.andy.passport.service.SystemRoleService;
import com.andy.passport.vo.SystemRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRole> implements SystemRoleService {


    /**
     * 分页查询角色
     * @param page
     * @param systemRoleQueryDto
     * @return
     */
    @Override
    public IPage<SystemRole> page(Page<SystemRole> page, SystemRoleQueryDto systemRoleQueryDto) {
        LambdaQueryWrapper<SystemRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(systemRoleQueryDto.getRoleName()), SystemRole::getRoleName, systemRoleQueryDto.getRoleName())
                .in(SystemRole::getStatus, Arrays.asList(BaseEntityConstants.Ok_Disable));
        return super.page(page, queryWrapper);
    }

    /**
     * 编辑角色
     * @param systemRoleEditDto
     * @return
     */
    @Override
    public Integer edit(SystemRoleEditDto systemRoleEditDto){
        if(this.checkRoleName(systemRoleEditDto.getId(), systemRoleEditDto.getRoleName())){
            throw new BusinessException("角色名称不能重复");
        }
        if(this.checkRoleCode(systemRoleEditDto.getId(), systemRoleEditDto.getRoleCode())){
            throw new BusinessException("角色编码不能重复");
        }
        SystemRole systemRole;
        if(null != systemRoleEditDto.getId()){
            systemRole = super.getById(systemRoleEditDto.getId());
            BeanUtil.copyProperties(systemRole, systemRoleEditDto);
//            systemRole.setUpdateBy();
            systemRole.setUpdateTime(new Date());
        }else {
            systemRole = new SystemRole();
            BeanUtil.copyProperties(systemRoleEditDto, systemRole);
//            systemRole.setCreateBy();
            systemRole.setCreateTime(new Date());
        }
        super.saveOrUpdate(systemRole);
        return systemRole.getId();
    }


    /**
     * 判断角色名称不能重复
     * @param id
     * @param roleName
     * @return
     */
    @Override
    public boolean checkRoleName(Integer id, String roleName){
        LambdaQueryWrapper<SystemRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemRole::getRoleName, roleName)
                .ne(null != id, SystemRole::getId, id)
                .eq(SystemRole::getStatus, BaseEntityConstants.Status_OK);
        return this.count(queryWrapper) > 0;
    }

    /**
     * 判断角色编码不能重复
     * @param id
     * @param roleCode
     * @return
     */
    @Override
    public boolean checkRoleCode(Integer id, String roleCode){
        LambdaQueryWrapper<SystemRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemRole::getRoleCode, roleCode)
                .ne(null != id, SystemRole::getId, id)
                .eq(SystemRole::getStatus, BaseEntityConstants.Status_OK);
        return this.count(queryWrapper) > 0;
    }

    /**
     * 逻辑删除角色
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Integer id) {
        SystemRole systemRole = super.getById(id);
        if(null == systemRole){
            throw new BusinessException("数据错误");
        }
        systemRole.setStatus(BaseEntityConstants.Status_Remove);
        systemRole.setUpdateTime(new Date());
//        systemRole.setUpdateBy();
        super.updateById(systemRole);
        return true;
    }

    /**
     * 禁用/启用角色
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean disable(Integer id, Integer status) {
        SystemRole systemRole = super.getById(id);
        if(null == systemRole){
            throw new BusinessException("数据错误");
        }
        if(!BaseEntityConstants.Status_Disable.equals(status) && !BaseEntityConstants.Status_OK.equals(status)){
            throw new BusinessException("状态错误");
        }
        systemRole.setStatus(status);
        systemRole.setUpdateTime(new Date());
//        systemRole.setUpdateBy();
        super.updateById(systemRole);
        return true;
    }
}
