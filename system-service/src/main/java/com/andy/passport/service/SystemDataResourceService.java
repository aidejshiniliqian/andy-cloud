package com.andy.passport.service;

import com.andy.passport.dto.SystemDataResourceEditDto;
import com.andy.passport.dto.SystemDataResourceQueryDto;
import com.andy.passport.entity.SystemDataResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 数据资源表 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemDataResourceService extends IService<SystemDataResource> {

    IPage<SystemDataResource> page(Page<SystemDataResource> page, SystemDataResourceQueryDto systemDataResourceQueryDto);

    SystemDataResource queryInfo(Integer id);

    Integer edit(SystemDataResourceEditDto systemDataResourceEditDto);

    Boolean delete(Integer id);

    Boolean disable(Integer id, Integer status);

    Boolean checkDataName(Integer id, String dataName);
}
