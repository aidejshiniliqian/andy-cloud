package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.CommonResult;
import com.andy.common.utils.BaseTreeHelper;
import com.andy.passport.dto.SystemMenuEditDto;
import com.andy.passport.entity.SystemMenu;
import com.andy.passport.service.SystemMenuService;
import com.andy.passport.vo.SystemMenuTreeVo;
import com.andy.passport.vo.SystemMenuVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 功能菜单表 前端控制器
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@RestController
@RequestMapping("/menu")
@Tag(name = "功能菜单表", description = "功能菜单表")
public class SystemMenuController {

    @Resource
    private SystemMenuService systemMenuService;

    /**
     * 查询子系统功能菜单树（含禁用的）
     * @return
     */
    @GetMapping("/tree/all")
    @Operation(summary = "查询子系统功能菜单树（含禁用的）")
    public CommonResult<List<SystemMenuTreeVo>> queryAllTree(@RequestParam("subSystemCode") String subSystemCode){
        List<SystemMenu> systemMenus = systemMenuService.queryBySubSystemCode(subSystemCode, BaseEntityConstants.Ok_Disable);
        List<SystemMenuTreeVo> systemMenuTreeVos = new ArrayList<>();
        systemMenus.forEach(systemMenu -> {
            SystemMenuTreeVo systemMenuTreeVo = new SystemMenuTreeVo();
            BeanUtil.copyProperties(systemMenu, systemMenuTreeVo);
            systemMenuTreeVos.add(systemMenuTreeVo);
        });
        return CommonResult.success(BaseTreeHelper.build(systemMenuTreeVos).addSortColumn("sort").getTreeData());
    }

    /**
     * 查询子系统功能菜单树（仅正常的）
     * @return
     */
    @GetMapping("/tree")
    @Operation(summary = "查询子系统功能菜单树（仅正常的）")
    public CommonResult<List<SystemMenuTreeVo>> queryTree(@RequestParam("subSystemCode") String subSystemCode){
        List<SystemMenu> systemMenus = systemMenuService.queryBySubSystemCode(subSystemCode, BaseEntityConstants.Status_OK);
        List<SystemMenuTreeVo> systemMenuTreeVos = new ArrayList<>();
        systemMenus.forEach(systemMenu -> {
            SystemMenuTreeVo systemMenuTreeVo = new SystemMenuTreeVo();
            BeanUtil.copyProperties(systemMenu, systemMenuTreeVo);
            systemMenuTreeVos.add(systemMenuTreeVo);
        });
        return CommonResult.success(BaseTreeHelper.build(systemMenuTreeVos).getTreeData());
    }

    /**
     * 查询单个功能菜单
     * @param id
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "查询单个功能菜单")
    public CommonResult<SystemMenuVo> queryInfo(Integer id){
        SystemMenu systemMenu = systemMenuService.queryInfo(id);
        return CommonResult.success(BeanUtil.copyProperties(systemMenu, SystemMenuVo.class));
    }

    /**
     * 新增或编辑菜单
     * @param systemMenuEditDto
     * @return
     */
    @PostMapping("/edit")
    @Operation(summary = "新增或编辑菜单")
    public CommonResult<Integer> edit(@RequestBody SystemMenuEditDto systemMenuEditDto){
        return CommonResult.success(systemMenuService.edit(systemMenuEditDto));
    }

    /**
     * 启用/禁用
     * @param id
     * @param status
     * @return
     */
    @GetMapping("/enableOrDisable")
    @Operation(summary = "启用/禁用")
    public CommonResult<Boolean> disable(Integer id, Integer status){
        return CommonResult.success(systemMenuService.disable(id, status));
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @Operation(summary = "逻辑删除")
    public CommonResult<Boolean> delete(Integer id){
        return CommonResult.success(systemMenuService.delete(id));
    }

}
