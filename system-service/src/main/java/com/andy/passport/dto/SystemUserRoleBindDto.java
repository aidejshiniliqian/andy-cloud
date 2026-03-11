package com.andy.passport.dto;

import lombok.Data;

import java.util.List;

@Data
public class SystemUserRoleBindDto {
    private Integer userId;
    private List<Integer> roleIds;
}