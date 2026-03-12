package com.andy.passport.mapper;

import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.vo.SystemDataRoleRuleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色数据权限规则表 Mapper 接口
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemDataRoleRuleMapper extends BaseMapper<SystemDataRoleRule> {

    List<SystemDataRoleRuleVo> queryList(@Param("roleId") Integer roleId);

}
