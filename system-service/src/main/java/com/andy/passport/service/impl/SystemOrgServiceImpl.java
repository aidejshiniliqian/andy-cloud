package com.andy.passport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.BusinessException;
import com.andy.passport.dto.SystemOrgEditDto;
import com.andy.passport.entity.SystemOrg;
import com.andy.passport.mapper.SystemOrgMapper;
import com.andy.passport.service.SystemOrgService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Arrays;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织机构表 服务实现类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Service
public class SystemOrgServiceImpl extends ServiceImpl<SystemOrgMapper, SystemOrg> implements SystemOrgService {

    /**
     * 创建根目录
     * @param systemOrg
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createRootOrg(SystemOrg systemOrg) {
        //先判断根目录是否存在
        SystemOrg rootOrg = this.queryRootOrg();
        if(null != rootOrg){
            throw new BusinessException("根目录已经存在，不允许继续添加");
        }
        if(this.checkOrgCode(systemOrg.getId(), systemOrg.getOrgCode())){
            throw new BusinessException("组织机构编码不允许重复");
        }
//        systemOrg.setCreateBy();
        systemOrg.setCreateTime(new Date());
        systemOrg.setStatus(BaseEntityConstants.Status_OK);
        systemOrg.setParentId(0);
        super.save(systemOrg);
        return systemOrg.getId();
    }

    /**
     * 查询根目录
     * @return
     */
    @Override
    public SystemOrg queryRootOrg() {
        LambdaQueryWrapper<SystemOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemOrg::getParentId, 0)
                .eq(SystemOrg::getStatus, BaseEntityConstants.Status_OK);
        return super.getOne(queryWrapper, false);
    }

    /**
     * 查询所有组织机构
     * @return
     */
    @Override
    public List<SystemOrg> queryAllOrg(Integer ... status) {
        LambdaQueryWrapper<SystemOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SystemOrg::getStatus, Arrays.asList(status));
        return super.list(queryWrapper);
    }

    /**
     * 查询单个组织机构
     * @param id
     * @return
     */
    @Override
    public SystemOrg queryInfo(Integer id){
        SystemOrg systemOrg = super.getById(id);
        if(null == systemOrg){
            throw new BusinessException("数据错误");
        }
        return systemOrg;
    }

    /**
     * 新增或编辑组织机构
     *
     * @param systemOrgEditDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer edit(SystemOrgEditDto systemOrgEditDto) {
        if(this.checkOrgCode(systemOrgEditDto.getId(), systemOrgEditDto.getOrgCode())){
            throw new BusinessException("组织机构编码不允许重复");
        }
        SystemOrg systemOrg;
        if(null == systemOrgEditDto.getId()){
            systemOrg = new SystemOrg();
            BeanUtil.copyProperties(systemOrgEditDto, systemOrg);
//            systemOrg.setCreateBy();
            systemOrg.setCreateTime(new Date());
            systemOrg.setStatus(BaseEntityConstants.Status_OK);
        }else {
            systemOrg = super.getById(systemOrgEditDto.getId());
            BeanUtil.copyProperties(systemOrgEditDto, systemOrg);
//            systemOrg.setUpdateBy();
            systemOrg.setUpdateTime(new Date());
        }
        super.saveOrUpdate(systemOrg);
        return systemOrgEditDto.getId();
    }

    /**
     * 启用/禁用（含子集）
     * @param id
     * @param status
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean disable(Integer id, Integer status){
        SystemOrg systemOrg = super.getById(id);
        if(null == systemOrg){
            throw new BusinessException("数据错误");
        }
        if(!BaseEntityConstants.Status_Disable.equals(status) && !BaseEntityConstants.Status_OK.equals(status)){
            throw new BusinessException("状态错误");
        }
        systemOrg.setStatus(status);
//        systemOrg.setUpdateBy();
        systemOrg.setUpdateTime(new Date());
        super.updateById(systemOrg);
        //递归所有子集，改变状态
        List<SystemOrg> children = super.baseMapper.recursionOrgById(systemOrg.getId());
        if(CollectionUtil.isNotEmpty(children)){
            List<Integer> childrenIds = children.stream().map(SystemOrg::getId).collect(Collectors.toList());
            SystemOrg updateOrg = new SystemOrg();
            updateOrg.setStatus(status);
            UpdateWrapper<SystemOrg> updateWrapper = new UpdateWrapper<>();
            updateWrapper.in("id", childrenIds);
            super.update(updateOrg, updateWrapper);
        }
        return true;
    }

    /**
     * 删除（含子集）
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id){
        SystemOrg systemOrg = super.getById(id);
        if(null == systemOrg){
            throw new BusinessException("数据错误");
        }
        systemOrg.setStatus(BaseEntityConstants.Status_Remove);
//        systemOrg.setUpdateBy();
        systemOrg.setUpdateTime(new Date());
        super.updateById(systemOrg);
        //递归所有子集，改变状态
        List<SystemOrg> children = super.baseMapper.recursionOrgById(systemOrg.getId());
        if(CollectionUtil.isNotEmpty(children)){
            List<Integer> childrenIds = children.stream().map(SystemOrg::getId).collect(Collectors.toList());
            SystemOrg updateOrg = new SystemOrg();
            updateOrg.setStatus(BaseEntityConstants.Status_Remove);
            UpdateWrapper<SystemOrg> updateWrapper = new UpdateWrapper<>();
            updateWrapper.in("id", childrenIds);
            super.update(updateOrg, updateWrapper);
        }
        return true;
    }

    /**
     * 判断组织机构code是否存在
     * @param id
     * @param orgCode
     * @return
     */
    @Override
    public Boolean checkOrgCode(Integer id, String orgCode) {
        LambdaQueryWrapper<SystemOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemOrg::getOrgCode, orgCode)
                .eq(SystemOrg::getStatus, BaseEntityConstants.Status_OK)
                .ne(null != id, SystemOrg::getId, id);
        return super.count(queryWrapper) > 0;
    }
}
