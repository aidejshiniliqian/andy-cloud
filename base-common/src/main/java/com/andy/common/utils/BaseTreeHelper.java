package com.andy.common.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: liuxp
 * @Date: 2020/09/15 15:07
 */
public class BaseTreeHelper<T extends BaseTree<T>> {

    private String parentColumn = "parentId";

    private String childColumn = "id";

    private final List<T> datas = new ArrayList<>();

    private final Map<String, String> sortsColumns = new LinkedHashMap<>();

    public static <T extends BaseTree<T>> BaseTreeHelper<T> build(List<T> datas) {
        BaseTreeHelper<T> baseTreeHelper = new BaseTreeHelper<>();
        baseTreeHelper.addHandleData(datas);
        return baseTreeHelper;
    }

    public BaseTreeHelper<T> addHandleData(List<T> datas) {
        if (!CollectionUtils.isEmpty(this.datas)) {
            this.datas.clear();
        }
        this.datas.addAll(datas);
        return this;
    }

    public BaseTreeHelper<T> setParentColumn(String parentColumn) {
        this.parentColumn = parentColumn;
        return this;
    }

    public BaseTreeHelper<T> setChildColumn(String childColumn) {
        this.childColumn = childColumn;
        return this;
    }

    public BaseTreeHelper<T> addSortColumn(String column) {
        this.addSortColumn(column, "asc");
        return this;
    }

    public BaseTreeHelper<T> addSortColumn(String column, String type) {
        sortsColumns.put(column, type);
        return this;
    }

    public List<T> getTreeData() {
        if (CollectionUtils.isEmpty(datas)) {
            return new ArrayList<>();
        }
        Map<Object, List<T>> groupByParent = datas
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy((item) -> {
                    BeanWrapper beanWrapper = new BeanWrapperImpl(item);
                    return beanWrapper.getPropertyValue(parentColumn);
                }));
        Map<Object, T> groupByData = datas
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap((item) -> {
                    BeanWrapper beanWrapper = new BeanWrapperImpl(item);
                    return beanWrapper.getPropertyValue(childColumn);
                }, t -> t));
        LambdaValue<List<T>> lambdaValue = LambdaValue.build(new ArrayList<>());
        groupByParent.forEach((k, v) -> {
            if (groupByData.containsKey(k)) {
                // 为子节点
                Object dataObj = groupByData.get(k);
                BeanWrapper beanWrapper = new BeanWrapperImpl(dataObj);
                List<T> children = (List<T>) beanWrapper.getPropertyValue("children");
                children.addAll(groupByParent.get(k));
                // 按照指定规则排序
                this.sortData(children);
                beanWrapper.setPropertyValue("children", children);
            } else {
                // 为第一层父节点
                lambdaValue.getV().addAll(groupByParent.get(k));
            }
        });
        // 对第一层节点按照指定规则排序
        this.sortData(lambdaValue.getV());
        return lambdaValue.getV();
    }

    /**
     * 对数据进行排序
     * @param childDatas
     */
    private void sortData(List<T> childDatas) {
        if (!CollectionUtils.isEmpty(childDatas)) {
            childDatas.sort((t1, t2) -> {
                int compare = 0;
                for (Map.Entry<String, String> entry : sortsColumns.entrySet()) {
                    boolean isAsc = !entry.getValue().equalsIgnoreCase("desc");
                    // 倒序
                    compare = compareObject(entry.getKey(), isAsc, t1, t2);
                    if (compare != 0) {
                        break;
                    }
                }
                return compare;
            });
        }
    }

    /**
     * 对两个对象按照指定名称排序
     *
     * @param columnName 属性名称
     * @param isAsc      true是升序,false是降序
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    private <T> int compareObject(final String columnName, boolean isAsc, T t1, T t2) {
        int compareResult;
        BeanWrapper beanWrapperT1 = new BeanWrapperImpl(t1);
        BeanWrapper beanWrapperT2 = new BeanWrapperImpl(t2);
        Object val1 = beanWrapperT1.getPropertyValue(columnName);
        Object val2 = beanWrapperT2.getPropertyValue(columnName);
        String str1 = val1.toString();
        String str2 = val2.toString();
        if (val1 instanceof Date && val2 instanceof Date) {
            long time1 = ((Date) val1).getTime();
            long time2 = ((Date) val2).getTime();
            int maxLength = Long.toString(Math.max(time1, time2)).length();
            str1 = this.addZero2Str(time1, maxLength);
            str2 = this.addZero2Str(time2, maxLength);
        } else if (val1 instanceof Number && val2 instanceof Number) {
            int maxLength = Math.max(str1.length(), str2.length());
            str1 = this.addZero2Str((Number) val1, maxLength);
            str2 = this.addZero2Str((Number) val2, maxLength);
        }
        if (isAsc) {
            compareResult = str1.compareTo(str2);
        } else {
            compareResult = str2.compareTo(str1);
        }
        return compareResult;
    }

    /**
     * 给数字对象按照指定长度在左侧补0
     * 例：addZero2Str(11,4) 返回 "0011", addZero2Str(-18,6)返回 "-000018"
     *
     * @param numObj
     * @param length
     * @return
     */
    private String addZero2Str(Number numObj, int length) {
        NumberFormat nf = NumberFormat.getInstance();
        // 设置是否使用分组
        nf.setGroupingUsed(false);
        // 设置最大整数位数
        nf.setMaximumIntegerDigits(length);
        // 设置最小整数位数
        nf.setMinimumIntegerDigits(length);
        return nf.format(numObj);
    }
}
