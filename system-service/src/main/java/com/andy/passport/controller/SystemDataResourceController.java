package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemDataResourceEditDto;
import com.andy.passport.dto.SystemDataResourceQueryDto;
import com.andy.passport.entity.SystemDataResource;
import com.andy.passport.service.SystemDataResourceService;
import com.andy.passport.vo.SystemDataResourceVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 数据资源表 前端控制器
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@RestController
@RequestMapping("/data/resource")
@Tag(name = "数据资源表", description = "数据资源表")
public class SystemDataResourceController {

    @Resource
    private SystemDataResourceService systemDataResourceService;

    /**
     * 分页查询数据资源表
     * @param page
     * @param systemDataResourceQueryDto
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询数据资源表")
    public CommonResult<IPage<SystemDataResourceVo>> queryPage(Page<SystemDataResource> page,
                                                               SystemDataResourceQueryDto systemDataResourceQueryDto){
        IPage<SystemDataResource> systemDataResourceIPage = systemDataResourceService.page(page, systemDataResourceQueryDto);
        return CommonResult.success(systemDataResourceIPage.convert(systemDataResource -> BeanUtil.copyProperties(systemDataResource, SystemDataResourceVo.class)));
    }

    /**
     * 查询单个数据资源
     * @param id
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "查询单个数据资源")
    public CommonResult<SystemDataResourceVo> queryInfo(Integer id){
        SystemDataResource systemDataResource = systemDataResourceService.getById(id);
        return CommonResult.success(BeanUtil.copyProperties(systemDataResource, SystemDataResourceVo.class));
    }

    /**
     * 新增或编辑数据资源
     * @param systemDataResourceEditDto
     * @return
     */
    @PostMapping("/edit")
    @Operation(summary = "新增或编辑数据资源")
    public CommonResult<Integer> edit(@RequestBody SystemDataResourceEditDto systemDataResourceEditDto){
        return CommonResult.success(systemDataResourceService.edit(systemDataResourceEditDto));
    }

    /**
     * 启用/禁用数据资源
     * @param id
     * @param status
     * @return
     */
    @GetMapping("/enableOrDisable")
    @Operation(summary = "启用/禁用数据资源")
    public CommonResult<Boolean> enable(Integer id, Integer status){
        return CommonResult.success(systemDataResourceService.disable(id, status));
    }

    /**
     * 删除数据资源
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @Operation(summary = "删除数据资源")
    public CommonResult<Boolean> delete(@RequestParam("id") Integer id){
        return CommonResult.success(systemDataResourceService.delete(id));
    }

}
