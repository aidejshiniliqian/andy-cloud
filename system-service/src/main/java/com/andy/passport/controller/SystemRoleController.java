package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemRoleEditDto;
import com.andy.passport.dto.SystemRoleQueryDto;
import com.andy.passport.entity.SystemRole;
import com.andy.passport.service.SystemRoleService;
import com.andy.passport.vo.SystemRoleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@RestController
@RequestMapping("/role")
@Tag(name = "系统角色表", description = "系统角色表")
public class SystemRoleController {

    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 分页查询角色
     * @param page
     * @param systemRoleQueryDto
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询角色")
    public CommonResult<IPage<SystemRoleVo>> queryPage(Page<SystemRole> page,
                                                       SystemRoleQueryDto systemRoleQueryDto) {
        IPage<SystemRole> systemRoleIPage = systemRoleService.page(page, systemRoleQueryDto);
        return CommonResult.success(systemRoleIPage.convert(systemRole -> BeanUtil.copyProperties(systemRole, SystemRoleVo.class)));
    }

    /**
     * 查询单个角色
     * @param id
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "查询单个角色")
    public CommonResult<SystemRoleVo> queryInfo(@RequestParam("id") Integer id){
        return CommonResult.success(BeanUtil.copyProperties(systemRoleService.getById(id), SystemRoleVo.class));
    }

    /**
     * 新增或编辑角色
     * @param systemRoleEditDto
     * @return
     */
    @PostMapping("/edit")
    @Operation(summary = "新增或编辑角色")
    public CommonResult<Integer> edit(@RequestBody SystemRoleEditDto systemRoleEditDto) {
        return CommonResult.success(systemRoleService.edit(systemRoleEditDto));
    }

    /**
     * 逻辑删除角色
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @Operation(summary = "逻辑删除角色")
    public CommonResult<Boolean> delete(@RequestParam("id") Integer id){
        return CommonResult.success(systemRoleService.delete(id));
    }

    /**
     * 禁用/启用角色
     * @param id
     * @return
     */
    @GetMapping("/enableOrDisable")
    @Operation(summary = "禁用/启用角色")
    public CommonResult<Boolean> disable(@RequestParam("id") Integer id,
                                              @RequestParam("status") Integer status){
        return CommonResult.success(systemRoleService.disable(id, status));
    }
}
