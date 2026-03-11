package com.andy.passport.mapper;

import com.andy.passport.entity.SystemOrg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织机构表 Mapper 接口
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemOrgMapper extends BaseMapper<SystemOrg> {

    List<SystemOrg> recursionOrgById(@Param("orgId") Integer orgId);
}
