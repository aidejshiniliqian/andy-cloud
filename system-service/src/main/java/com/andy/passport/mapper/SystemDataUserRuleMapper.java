package com.andy.passport.mapper;

import com.andy.passport.entity.SystemDataUserRule;
import com.andy.passport.vo.SystemDataUserRuleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户数据权限规则表 Mapper 接口
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemDataUserRuleMapper extends BaseMapper<SystemDataUserRule> {

    List<SystemDataUserRuleVo> queryList(@Param("userId") Integer userId);

}
