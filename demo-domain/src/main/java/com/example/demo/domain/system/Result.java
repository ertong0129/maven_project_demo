package com.example.demo.domain.system;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;


/**
 * Created by jintian on 2017/5/27.
 */
public class Result<T> implements Serializable {

    /**
     * 状态
     */
    private boolean status;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 返回值
     */
    private int responseCode;

    /**
     * 结果对象
     */
    private T entry;

    private int count;

    @Deprecated
    public static Result success() {
        Result result = new Result();
        result.setStatus(true);
        return result;
    }

    @Deprecated
    public static Result success(Object entry, int count) {
        Result result = new Result();
        result.setCount(count);
        result.setEntry(entry);
        result.setStatus(true);
        return result;
    }

    @Deprecated
    public static Result success(Object entry) {
        Result result = new Result();
        result.setStatus(true);
        result.setEntry(entry);
        return result;
    }

    /**
     * 推荐使用 消除类型校验出现idea 提示
     *
     * @param entry 实体
     * @param count 查询时总数量
     * @param <T>
     * @return t
     */
    public static <T> Result<T> buildSuccess(T entry, int count) {
        Result<T> result = new Result<>();
        result.setStatus(true);
        result.setEntry(entry);
        result.setCount(count);
        return result;
    }

    /**
     * 推荐使用 消除类型校验出现idea 提示
     *
     * @param entry 实体
     * @param <T>   类型
     * @return Result
     */
    public static <T> Result<T> buildSuccess(T entry) {
        return buildSuccess(entry, 0);
    }

    /**
     * 推荐使用 消除类型校验出现idea 提示
     *
     * @param <T> 实体类型
     * @return
     */
    public static <T> Result<T> buildSuccess() {
        return buildSuccess(null, 0);
    }

    public static Result success(Object entry, String message) {
        Result result = new Result();
        result.setStatus(true);
        result.setEntry(entry);
        result.setMessage(message);
        return result;
    }

    public static Result fail(int code, String msg) {
        Result result = new Result();
        result.setStatus(false);
        result.setMessage(msg);
        result.setResponseCode(code);

        return result;
    }

    public static Result fail(int code, String msg, int count) {
        Result result = new Result();
        result.setStatus(false);
        result.setMessage(msg);
        result.setResponseCode(code);
        result.setCount(count);

        return result;
    }

    @Deprecated
    public static Result fail(Object object) {
        Result result = new Result();
        result.setEntry(object);
        result.setStatus(false);
        return result;
    }

    public static <T> Result<T> buildFail(String msg) {
        Result<T> result = new Result<>();
        result.setStatus(false);
        result.setMessage(msg);
        result.setResponseCode(ErrorEnum.DEFAULT_ERROR.getValue());
        return result;
    }

    /**
     * 推荐使用
     * @param errorEnum
     * @param <T>
     * @return
     */
    public static <T> Result<T> buildFail(ErrorEnum errorEnum) {
        Result<T> result = new Result<>();
        result.setStatus(false);
        result.setMessage(errorEnum.getName());
        result.setResponseCode(errorEnum.getValue());
        return result;
    }

    public static <T> Result<T> fail() {
        return buildFail(ErrorEnum.DEFAULT_ERROR);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public T getEntry() {
        return entry;
    }

    public void setEntry(T entry) {
        this.entry = entry;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        String str;
        try {
            str = JSON.toJSONString(this);
        } catch (RuntimeException e) {
            str = "";
        }
        return str;
    }
}