package com.andy.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessBaseEntity extends BaseEntity{

    @Schema(name = "所属组织机构")
    @TableField("org_id")
    protected Integer orgId;

}
