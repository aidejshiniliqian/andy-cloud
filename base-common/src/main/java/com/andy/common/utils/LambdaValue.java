package com.andy.common.utils;

import lombok.Data;

/**
 * @Author Andy.Liu
 * @Date 2019/2/14 13:57
 */
@Data
public class LambdaValue<T> {
    private T v;

    private LambdaValue(){
        super();
    }

    public static <T> LambdaValue<T> build(T v){
        LambdaValue<T> value = new LambdaValue<>();
        value.setV(v);
        return value;
    }
}
