package com.example.demo.exception;

/**
 * Created by linziwei on 2017/4/27.
 */
public enum ErrorEnum {
    DEFAULT_ERROR(100, "默认错误"),

    PARAMS_ERROR(101, "参数错误"),

    HAS_NO_POWER(102, "没有权限操作"),

    DB_ERROR(103, "数据库操作错误"),

    DATA_NOT_EXIST(104, "数据不存在"),


    NET_ERROR(105, "网络异常，请稍后重试"),

    OUT_OF_MAX_PAGESIZE(106, "超出最大查询数量"),

    USER_NOT_LOGIN(10212, "用户未登录");
    /**
     * 值
     */
    private int value;

    /**
     * 名称
     */
    private String name;


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ErrorEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
