package com.example.demo.domain.query;

import java.io.Serializable;

public class BaseQuery implements Serializable {
    /**
     * 页码
     */
    protected Integer pageNo = 1;

    /**
     * 每页个数
     */
    protected Integer pageSize = 10;
    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 排序sql
     */
    private String orderBySql;

    /**
     * 分页数据偏移量
     */
    public Integer getOffset() {
        computeOffset();
        return offset;
    }

    /**
     * 计算分页偏移量
     */
    public Integer computeOffset() {
        if (null == pageNo || null == pageSize) {
            return null;
        }
        offset = (pageNo - 1) * pageSize;
        return offset;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        computeOffset();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        computeOffset();
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getOrderBySql() {
        return orderBySql;
    }

    public void setOrderBySql(String orderBySql) {
        this.orderBySql = orderBySql;
    }
}
