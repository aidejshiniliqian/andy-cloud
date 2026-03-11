package com.andy.passport.mapper;

import com.andy.passport.entity.SystemRole;
import com.andy.passport.entity.SystemUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemUserRoleMapper extends BaseMapper<SystemUserRole> {

    /**
     * 根据用户ID查询角色列表
     * @param wrapper 查询条件包装器
     * @return 角色列表
     */
    List<SystemRole> selectRolesByUserId(@Param(Constants.WRAPPER) Wrapper<?> wrapper);

}
