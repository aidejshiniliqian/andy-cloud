package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.entity.BaseEntityConstants;
import com.andy.common.exception.CommonResult;
import com.andy.common.utils.BaseTree;
import com.andy.common.utils.BaseTreeHelper;
import com.andy.passport.dto.SystemOrgEditDto;
import com.andy.passport.entity.SystemOrg;
import com.andy.passport.service.SystemOrgService;
import com.andy.passport.vo.SystemOrgTreeVo;
import com.andy.passport.vo.SystemOrgVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织机构表 前端控制器
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@RestController
@RequestMapping("/org")
@Tag(name = "组织机构管理", description = "组织机构管理")
public class SystemOrgController {

    @Resource
    private SystemOrgService systemOrgService;

    /**
     * 查询组织机构树（含禁用的）
     * @return
     */
    @GetMapping("/tree/all")
    @Operation(summary = "查询组织机构树（含禁用的）")
    public CommonResult<List<SystemOrgTreeVo>> queryAllTree(){
        List<SystemOrg> systemOrgs = systemOrgService.queryAllOrg(BaseEntityConstants.Ok_Disable);
        List<SystemOrgTreeVo> systemOrgTreeVos = systemOrgs.stream().map(systemOrg -> {
            SystemOrgTreeVo systemOrgTreeVo = new SystemOrgTreeVo();
            BeanUtil.copyProperties(systemOrg, systemOrgTreeVo);
            return systemOrgTreeVo;
        }).collect(Collectors.toList());
        return CommonResult.success(BaseTreeHelper.build(systemOrgTreeVos).addSortColumn("sort").getTreeData());
    }

    /**
     * 查询组织机构树（仅正常的）
     * @return
     */
    @GetMapping("/tree")
    @Operation(summary = "查询组织机构树（仅正常的）")
    public CommonResult<List<SystemOrgTreeVo>> queryTree(){
        List<SystemOrg> systemOrgs = systemOrgService.queryAllOrg(BaseEntityConstants.Status_OK);
        List<SystemOrgTreeVo> systemOrgTreeVos = systemOrgs.stream().map(systemOrg -> {
            SystemOrgTreeVo systemOrgTreeVo = new SystemOrgTreeVo();
            BeanUtil.copyProperties(systemOrg, systemOrgTreeVo);
            return systemOrgTreeVo;
        }).collect(Collectors.toList());
        return CommonResult.success(BaseTreeHelper.build(systemOrgTreeVos).addSortColumn("sort").getTreeData());
    }

    /**
     * 创建组织机构根目录（仅支持创建一个）
     * @param systemOrgEditDto
     * @return
     */
    @PostMapping("/create/root")
    @Operation(summary = "创建组织机构根目录（仅支持创建一个）")
    public CommonResult<Integer> createRoot(@RequestBody SystemOrgEditDto systemOrgEditDto){
        SystemOrg systemOrg = BeanUtil.copyProperties(systemOrgEditDto, SystemOrg.class);
        return CommonResult.success(systemOrgService.createRootOrg(systemOrg));
    }

    /**
     * 查询单个组织机构
     * @param id
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "查询单个组织机构")
    public CommonResult<SystemOrgVo> queryInfo(@RequestParam("id") Integer id){
        SystemOrg systemOrg = systemOrgService.queryInfo(id);
        return CommonResult.success(BeanUtil.copyProperties(systemOrg, SystemOrgVo.class));
    }

    /**
     * 新增或编辑组织机构
     * @param systemOrgEditDto
     * @return
     */
    @PostMapping("/edit")
    @Operation(summary = "新增或编辑组织机构")
    public CommonResult<Integer> edit(@RequestBody SystemOrgEditDto systemOrgEditDto){
        return CommonResult.success(systemOrgService.edit(systemOrgEditDto));
    }

    /**
     * 删除组织机构
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @Operation(summary = "删除组织机构")
    public CommonResult<Boolean> delete(@RequestParam("id") Integer id){
        return CommonResult.success(systemOrgService.delete(id));
    }

    /**
     * 禁用/启用组织机构
     * @param id
     * @param status
     * @return
     */
    @GetMapping("/disable")
    @Operation(summary = "禁用/启用组织机构")
    public CommonResult<Boolean> disable(@RequestParam("id") Integer id,
                                         @RequestParam("status") Integer status){
        return CommonResult.success(systemOrgService.disable(id, status));
    }
}
