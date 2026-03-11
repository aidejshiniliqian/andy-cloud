package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemUserEditDto;
import com.andy.passport.dto.SystemUserQueryDto;
import com.andy.passport.entity.SystemUser;
import com.andy.passport.service.SystemUserService;
import com.andy.passport.vo.SystemUserVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户管理")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    /**
     * 分页查询用户
     * @param page
     * @param systemUserQueryDto
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户")
    public CommonResult<IPage<SystemUserVo>> page(Page<SystemUser> page,
                                                  SystemUserQueryDto systemUserQueryDto) {
        IPage<SystemUser> iPage = systemUserService.page(page, systemUserQueryDto);
        return CommonResult.success(iPage.convert(systemUser -> BeanUtil.copyProperties(systemUser, SystemUserVo.class)));
    }

    /**
     * 查询单个用户
     * @param id
     * @return
     */
    @GetMapping("info")
    @Operation(summary = "查询单个用户")
    public CommonResult<SystemUserVo> queryInfo(@RequestParam("id") Integer id) {
        SystemUser systemUser = systemUserService.getById(id);
        return CommonResult.success(BeanUtil.copyProperties(systemUser, SystemUserVo.class));
    }

    /**
     * 新增或编辑用户
     * @param systemUserEditDto
     * @return
     */
    @PostMapping
    @Operation(summary = "新增或编辑用户")
    public CommonResult<Integer> editUser(@RequestBody SystemUserEditDto systemUserEditDto){
        return CommonResult.success(systemUserService.editUser(systemUserEditDto));
    }

    /**
     * 逻辑删除用户
     * @param id
     * @return
     */
    @GetMapping("delete")
    @Operation(summary = "逻辑删除用户")
    public CommonResult<Boolean> delete(@RequestParam("id") Integer id){
        return CommonResult.success(systemUserService.delete(id));
    }

    /**
     * 禁用/启用用户
     * @param id
     * @return
     */
    @GetMapping("enableOrDisable")
    @Operation(summary = "禁用/启用用户")
    public CommonResult<Boolean> disable(@RequestParam("id") Integer id,
                                         @RequestParam("status") Integer status){
        return CommonResult.success(systemUserService.disable(id, status));
    }

    /**
     * 根据用户账号查询用户信息
     * @param userCode 用户账号
     * @return 用户信息
     */
    @GetMapping("getByUserCode")
    @Operation(summary = "根据用户账号查询用户信息")
    public CommonResult<SystemUser> getByUserCode(@RequestParam("userCode") String userCode) {
        SystemUser systemUser = systemUserService.getByUserCode(userCode);
        return CommonResult.success(systemUser);
    }

}
