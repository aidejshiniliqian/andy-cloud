package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemDataRoleRuleBindDto;
import com.andy.passport.entity.SystemDataResource;
import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.service.SystemDataResourceService;
import com.andy.passport.service.SystemDataRoleRuleService;
import com.andy.passport.vo.SystemDataRoleRuleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data/role/rule")
@Tag(name = "角色数据权限规则表", description = "角色数据权限规则表")
public class SystemDataRoleRuleController {

    @Autowired
    private SystemDataRoleRuleService systemDataRoleRuleService;

    @Autowired
    private SystemDataResourceService systemDataResourceService;

    @GetMapping("/list/{roleId}")
    @Operation(summary = "查询角色数据权限规则列表")
    public CommonResult<List<SystemDataRoleRuleVo>> getByRoleId(@PathVariable("roleId") Integer roleId) {
        List<SystemDataRoleRule> roleRules = systemDataRoleRuleService.getByRoleId(roleId);
        if (roleRules.isEmpty()) {
            return CommonResult.success(List.of());
        }

        List<Integer> dataIds = roleRules.stream()
                .map(SystemDataRoleRule::getDataId)
                .distinct()
                .collect(Collectors.toList());
        List<SystemDataResource> resources = systemDataResourceService.listByIds(dataIds);
        Map<Integer, SystemDataResource> resourceMap = resources.stream()
                .collect(Collectors.toMap(SystemDataResource::getId, r -> r));

        List<SystemDataRoleRuleVo> voList = roleRules.stream()
                .map(rule -> {
                    SystemDataRoleRuleVo vo = BeanUtil.copyProperties(rule, SystemDataRoleRuleVo.class);
                    SystemDataResource resource = resourceMap.get(rule.getDataId());
                    if (resource != null) {
                        vo.setDataName(resource.getDataName());
                        vo.setDataTable(resource.getDataTable());
                    }
                    return vo;
                })
                .collect(Collectors.toList());
        return CommonResult.success(voList);
    }

    @PostMapping("/bind")
    @Operation(summary = "绑定角色数据权限规则")
    public CommonResult<Boolean> bindRoleDataRules(@RequestBody SystemDataRoleRuleBindDto dto) {
        return CommonResult.success(systemDataRoleRuleService.bindRoleDataRules(dto));
    }
}
