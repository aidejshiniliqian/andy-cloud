package com.andy.passport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.BusinessException;
import com.andy.common.utils.BaseTreeHelper;
import com.andy.passport.dto.SystemMenuEditDto;
import com.andy.passport.entity.SystemMenu;
import com.andy.passport.mapper.SystemMenuMapper;
import com.andy.passport.service.SystemMenuService;
import com.andy.passport.vo.SystemMenuTreeVo;
import com.andy.passport.vo.SystemMenuVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 功能菜单表 服务实现类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {

    /**
     * 查询子系统功能菜单列表（不含按钮）
     *
     * @param subSystemCode
     * @return
     */
    @Override
    public List<SystemMenu> queryBySubSystemCode(String subSystemCode, Integer ... status) {
        LambdaQueryWrapper<SystemMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemMenu::getSubSystemCode, subSystemCode)
                .in(SystemMenu::getStatus, Arrays.asList(status))
                .ne(SystemMenu::getType, 3);
        return super.list(queryWrapper);

    }

    /**
     * 查询单个菜单数据
     * @param id
     * @return
     */
    @Override
    public SystemMenu queryInfo(Integer id){
        SystemMenu systemMenu = super.getById(id);
        if(null == systemMenu){
            throw new BusinessException("数据错误");
        }
        return systemMenu;
    }

    /**
     * 新增或编辑功能菜单
     * @param systemMenuEditDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer edit(SystemMenuEditDto systemMenuEditDto) {
        SystemMenu systemMenu;
        if(null != systemMenuEditDto.getId()){
            systemMenu = super.getById(systemMenuEditDto.getId());
            BeanUtil.copyProperties(systemMenuEditDto, systemMenu);
//            systemMenu.setUpdateBy();
            systemMenu.setUpdateTime(new Date());
        }else {
            systemMenu = new SystemMenu();
            BeanUtil.copyProperties(systemMenuEditDto, systemMenu);
//            systemMenu.setCreateBy();
            systemMenu.setCreateTime(new Date());
            systemMenu.setStatus(BaseEntityConstants.Status_OK);
        }
        super.saveOrUpdate(systemMenu);
        return systemMenu.getId();
    }

    /**
     * 启用/禁用
     * @param id
     * @param status
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disable(Integer id, Integer status){
        SystemMenu systemMenu = super.getById(id);
        if(null == systemMenu){
            throw new BusinessException("数据错误");
        }
        if(1 != status && 0 != status){
            throw new BusinessException("状态错误");
        }
//        systemMenu.setUpdateBy();
        systemMenu.setUpdateTime(new Date());
        systemMenu.setStatus(status);
        return super.updateById(systemMenu);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer id){
        SystemMenu systemMenu = super.getById(id);
        if(null == systemMenu){
            throw new BusinessException("数据错误");
        }
//        systemMenu.setUpdateBy();
        systemMenu.setUpdateTime(new Date());
        systemMenu.setStatus(BaseEntityConstants.Status_Remove);
        return super.updateById(systemMenu);
    }



}
