package com.andy.common.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuxp
 * @Date: 2020/09/15 15:05
 */
@Data
public class BaseTree<T> {

    protected List<T> children = new ArrayList<>();

}
