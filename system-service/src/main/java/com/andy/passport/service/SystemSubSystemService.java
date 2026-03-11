package com.andy.passport.service;

import com.andy.passport.dto.SystemSubSystemEditDto;
import com.andy.passport.dto.SystemSubSystemQueryDto;
import com.andy.passport.entity.SystemSubSystem;
import com.andy.passport.vo.SystemSubSystemVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 子系统 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-03-03
 */
public interface SystemSubSystemService extends IService<SystemSubSystem> {

    IPage<SystemSubSystem> queryPage(Page<SystemSubSystem> page, SystemSubSystemQueryDto systemSubSystemQueryDto);

    SystemSubSystem queryBySubSystemCode(String subSystemCode);

    SystemSubSystem queryInfo(Integer id);

    Integer edit(SystemSubSystemEditDto systemSubSystemEditDto);

    Boolean disable(Integer id, Integer status);

    Boolean delete(Integer id);

    Boolean checkSystemName(Integer id, String systemName);

    Boolean checkSystemCode(Integer id, String systemCode);
}
