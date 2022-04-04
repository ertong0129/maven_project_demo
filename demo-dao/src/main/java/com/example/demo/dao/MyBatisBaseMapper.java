package com.example.demo.dao;

import java.util.List;

/**
 * DAO公共基类，由MybatisGenerator自动生成请勿修改
 * @param <T>
 * @param <Q>
 */
public interface MyBatisBaseMapper<T, Q> {

    int deleteByPrimaryKey(Long id);

    int insertSelective(T record);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T record);

    int queryCount(Q query);

    List<T> queryList(Q query);
}