package com.andy.passport.service;

import com.andy.passport.dto.SystemMenuEditDto;
import com.andy.passport.entity.SystemMenu;
import com.andy.passport.vo.SystemMenuTreeVo;
import com.andy.passport.vo.SystemMenuVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 功能菜单表 服务类
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
public interface SystemMenuService extends IService<SystemMenu> {

    List<SystemMenu> queryBySubSystemCode(String subSystemCode, Integer ... status);

    SystemMenu queryInfo(Integer id);

    Integer edit(SystemMenuEditDto systemMenuEditDto);

    boolean disable(Integer id, Integer status);

    boolean delete(Integer id);
}
