package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemDataUserRuleBindDto;
import com.andy.passport.entity.SystemDataResource;
import com.andy.passport.entity.SystemDataUserRule;
import com.andy.passport.service.SystemDataResourceService;
import com.andy.passport.service.SystemDataUserRuleService;
import com.andy.passport.vo.SystemDataUserRuleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data/user/rule")
@Tag(name = "用户数据权限规则表", description = "用户数据权限规则表")
public class SystemDataUserRuleController {

    @Autowired
    private SystemDataUserRuleService systemDataUserRuleService;

    @Autowired
    private SystemDataResourceService systemDataResourceService;

    @GetMapping("/list/{userId}")
    @Operation(summary = "查询用户数据权限规则列表")
    public CommonResult<List<SystemDataUserRuleVo>> getByUserId(@PathVariable("userId") Integer userId) {
        List<SystemDataUserRule> userRules = systemDataUserRuleService.getByUserId(userId);
        if (userRules.isEmpty()) {
            return CommonResult.success(List.of());
        }

        List<Integer> dataIds = userRules.stream()
                .map(SystemDataUserRule::getDataId)
                .distinct()
                .collect(Collectors.toList());
        List<SystemDataResource> resources = systemDataResourceService.listByIds(dataIds);
        Map<Integer, SystemDataResource> resourceMap = resources.stream()
                .collect(Collectors.toMap(SystemDataResource::getId, r -> r));

        List<SystemDataUserRuleVo> voList = userRules.stream()
                .map(rule -> {
                    SystemDataUserRuleVo vo = BeanUtil.copyProperties(rule, SystemDataUserRuleVo.class);
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
    @Operation(summary = "绑定用户数据权限规则")
    public CommonResult<Boolean> bindUserDataRules(@RequestBody SystemDataUserRuleBindDto dto) {
        return CommonResult.success(systemDataUserRuleService.bindUserDataRules(dto));
    }

    @GetMapping("/final/list/{userId}")
    @Operation(summary = "查询用户最终数据权限（用户授权+角色授权并集）")
    public CommonResult<List<SystemDataUserRuleVo>> getFinalDataRules(@PathVariable("userId") Integer userId) {
        return CommonResult.success(systemDataUserRuleService.getFinalDataRulesByUserId(userId));
    }
}
