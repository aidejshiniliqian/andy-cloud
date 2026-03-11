package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemSubSystemEditDto;
import com.andy.passport.dto.SystemSubSystemQueryDto;
import com.andy.passport.entity.SystemSubSystem;
import com.andy.passport.service.SystemSubSystemService;
import com.andy.passport.vo.SystemSubSystemVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 子系统 前端控制器
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-03-03
 */
@RestController
@RequestMapping("/sub/system")
@Tag(name = "子系统管理", description = "子系统管理")
public class SystemSubSystemController {

    @Resource
    private SystemSubSystemService systemSubSystemService;

    /**
     * 分页查询子系统
     * @param page
     * @param queryDto
     * @return
     */
    @GetMapping("page")
    @Operation(summary = "分页查询子系统")
    public CommonResult<IPage<SystemSubSystemVo>> queryPage(Page<SystemSubSystem> page, SystemSubSystemQueryDto queryDto) {
        IPage<SystemSubSystem> systemSubSystemIPage = systemSubSystemService.queryPage(page, queryDto);
        return CommonResult.success(systemSubSystemIPage.convert(systemSubSystem -> {
            SystemSubSystemVo systemSubSystemVo = new SystemSubSystemVo();
            BeanUtil.copyProperties(systemSubSystem, systemSubSystemVo);
            return systemSubSystemVo;
        }));
    }

    /**
     * 查询单个子系统
     * @param id
     * @return
     */
    @GetMapping("info")
    @Operation(summary = "查询单个子系统")
    public CommonResult<SystemSubSystemVo> queryInfo(Integer id){
        SystemSubSystem systemSubSystem = systemSubSystemService.queryInfo(id);
        return CommonResult.success(BeanUtil.copyProperties(systemSubSystem, SystemSubSystemVo.class));
    }

    /**
     * 根据code查询单个子系统
     * @param subSystemCode
     * @return
     */
    @GetMapping("info/byCode")
    @Operation(summary = "根据code查询单个子系统")
    public CommonResult<SystemSubSystemVo> queryBySubSystemCode(String subSystemCode){
        SystemSubSystem systemSubSystem = systemSubSystemService.queryBySubSystemCode(subSystemCode);
        return CommonResult.success(BeanUtil.copyProperties(systemSubSystem, SystemSubSystemVo.class));
    }

    /**
     * 新增或编辑子系统
     * @param systemSubSystemEditDto
     * @return
     */
    @PostMapping("/edit")
    @Operation(summary = "新增或编辑子系统")
    public CommonResult<Integer> edit(@RequestBody SystemSubSystemEditDto systemSubSystemEditDto) {
        return CommonResult.success(systemSubSystemService.edit(systemSubSystemEditDto));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @Operation(summary = "删除子系统")
    public CommonResult<Boolean> delete(Integer id) {
        return CommonResult.success(systemSubSystemService.delete(id));
    }

    /**
     * 启用/禁用
     * @param id
     * @return
     */
    @GetMapping("/enableOrDisable")
    @Operation(summary = "启用/禁用")
    public CommonResult<Boolean> disable(Integer id, Integer status) {
        return CommonResult.success(systemSubSystemService.disable(id, status));
    }

}
