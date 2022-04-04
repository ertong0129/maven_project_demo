package com.example.demo.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @description 用户信息
 * @author yeqing
 * @date 2022-04-04
 */
@Data
public class DemoEntityDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 拓展字段
     */
    private String attributes;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * 更新锁
     */
    private Integer attributeCc;


}