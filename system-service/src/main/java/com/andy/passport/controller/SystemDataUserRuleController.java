package com.andy.passport.controller;

import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemDataUserRuleEditDto;
import com.andy.passport.service.SystemDataUserRuleService;
import com.andy.passport.vo.SystemDataUserFinalRuleVo;
import com.andy.passport.vo.SystemDataUserRuleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户数据权限规则表 前端控制器
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@RestController
@RequestMapping("/data/user/rule")
@Tag(name = "用户数据权限规则表", description = "用户数据权限规则表")
public class SystemDataUserRuleController {

    @Resource
    private SystemDataUserRuleService systemDataUserRuleService;

    /**
     * 查询用户下的数据权限列表
     * @param userId
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "查询用户下的数据权限列表")
    public CommonResult<List<SystemDataUserRuleVo>> queryList(@RequestParam Integer userId) {
        return CommonResult.success(systemDataUserRuleService.queryList(userId));
    }

    /**
     * 新增或编辑用户数据权限规则
     * @param systemDataUserRuleEditDto
     * @return
     */
    @PostMapping("/edit")
    @Operation(summary = "新增或编辑用户数据权限规则")
    public CommonResult<Boolean> edit(@RequestBody SystemDataUserRuleEditDto systemDataUserRuleEditDto) {
        return CommonResult.success(systemDataUserRuleService.edit(systemDataUserRuleEditDto));
    }

    /**
     * 查询用户最终的数据权限列表（用户数据权限和角色数据权限的并集）
     * @param userId
     * @return
     */
    @GetMapping("/final/list")
    @Operation(summary = "查询用户最终的数据权限列表")
    public CommonResult<List<SystemDataUserFinalRuleVo>> queryFinalList(@RequestParam Integer userId) {
        return CommonResult.success(systemDataUserRuleService.queryFinalList(userId));
    }

}
