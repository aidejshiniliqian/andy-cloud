package com.andy.passport.service;

import com.andy.passport.dto.SystemUserEditDto;
import com.andy.passport.dto.SystemUserQueryDto;
import com.andy.passport.entity.SystemUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemUserService extends IService<SystemUser> {

    IPage<SystemUser> page(Page<SystemUser> page, SystemUserQueryDto systemUserQueryDto);

    Integer editUser(SystemUserEditDto systemUserEditDto);

    Boolean checkUserCode(Integer id, String userCode);

    Boolean checkUserNumber(Integer id, String number);

    Boolean checkUserMobile(Integer id, String mobile);

    Boolean delete(Integer id);

    Boolean disable(Integer id, Integer status);

    /**
     * 根据用户账号查询用户信息
     * @param userCode 用户账号
     * @return 用户信息
     */
    SystemUser getByUserCode(String userCode);
}
