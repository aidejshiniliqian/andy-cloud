package com.andy.common.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseEntity implements Serializable {

    @Schema(name = "非业务主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;

    @Schema(name = "状态，0 ==正常，1==禁用，2==删除")
    @TableField("status")
    private Integer status;

    @Schema(name = "创建人")
    @TableField("create_by")
    protected Integer createBy;

    @Schema(name = "创建时间")
    @TableField("create_time")
    protected Date createTime;

    @Schema(name = "更新人")
    @TableField("update_by")
    protected Integer updateBy;

    @Schema(name = "更新时间")
    @TableField("update_time")
    protected Date updateTime;

}
