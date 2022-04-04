package com.example.demo.business.manager;

import java.util.List;

/**
 * @description manager通用
 * @author yeqing
 * @date 2022-04-04
 */
public interface BaseInterfaceManager<T, Q> {

    int deleteByPrimaryKey(Long id);

    int insertSelective(T record);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T record);

    int queryCount(Q query);

    List<T> queryList(Q query);

}
