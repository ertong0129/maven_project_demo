package com.example.demo.domain.query;

import lombok.Data;

import java.util.Date;

/**
 * @description 用户信息
 * @author yeqing
 * @date 2022-04-04
 */
@Data
public class DemoEntityQuery extends BaseQuery {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否删除
     */
    private Integer isDelete;


}