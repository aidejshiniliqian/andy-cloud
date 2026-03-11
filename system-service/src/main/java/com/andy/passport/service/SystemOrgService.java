package com.andy.passport.service;

import com.andy.passport.dto.SystemOrgEditDto;
import com.andy.passport.entity.SystemOrg;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 组织机构表 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemOrgService extends IService<SystemOrg> {

    Integer createRootOrg(SystemOrg systemOrg);

    SystemOrg queryRootOrg();

    List<SystemOrg> queryAllOrg(Integer... status);

    SystemOrg queryInfo(Integer id);

    @Transactional(rollbackFor = Exception.class)
    Boolean disable(Integer id, Integer status);

    @Transactional(rollbackFor = Exception.class)
    Integer edit(SystemOrgEditDto systemOrgEditDto);

    @Transactional(rollbackFor = Exception.class)
    Boolean delete(Integer id);

    Boolean checkOrgCode(Integer id, String orgCode);
}
