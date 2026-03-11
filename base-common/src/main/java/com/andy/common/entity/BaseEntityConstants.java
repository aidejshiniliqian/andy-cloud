package com.andy.common.entity;

import java.util.Arrays;
import java.util.List;

public class BaseEntityConstants {

    /**
     * 正常
     */
    public static final Integer Status_OK = 0;//正常
    /**
     * 禁用
     */
    public static final Integer Status_Disable = 1;
    /**
     * 已删除
     */
    public static final Integer Status_Remove = 2;

    /**
     * 正常和禁用的
     */
    public static final Integer[] Ok_Disable = new Integer[]{Status_OK, Status_Disable};
}
