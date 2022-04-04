package com.example.demo.business.manager.impl;

import com.example.demo.business.exception.DemoException;
import com.example.demo.business.manager.DemoEntityManager;
import com.example.demo.dao.DemoEntityMapper;
import com.example.demo.domain.convert.DemoEntityConvert;
import com.example.demo.domain.dto.DemoEntityDTO;

import com.example.demo.domain.entity.DemoEntityDO;
import com.example.demo.domain.query.DemoEntityQuery;
import com.example.demo.domain.system.ErrorEnum;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


/**
* @description 用户信息
* @author yeqing
* @date 2022-04-04
*/
@Service
public class DemoEntityManagerImpl implements DemoEntityManager {

    @Resource
    private DemoEntityMapper demoEntityMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        if (id == null) {
            throw new DemoException(ErrorEnum.PARAMS_ERROR);
        }
        return demoEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(DemoEntityDTO record) {
        DemoEntityDO demoEntityDO = DemoEntityConvert.dto2do(record);
        int n =  demoEntityMapper.insertSelective(demoEntityDO);
        record.setId(demoEntityDO.getId());
        return n;
    }

    @Override
    public DemoEntityDTO selectByPrimaryKey(Long id) {
        if (id == null) {
            throw new DemoException(ErrorEnum.PARAMS_ERROR);
        }
        return DemoEntityConvert.do2dto(demoEntityMapper.selectByPrimaryKey(id));
    }

    @Override
    public int updateByPrimaryKeySelective(DemoEntityDTO record) {
        if (record.getId() == null) {
            throw new DemoException(ErrorEnum.PARAMS_ERROR);
        }
        return demoEntityMapper.updateByPrimaryKeySelective(DemoEntityConvert.dto2do(record));
    }

    @Override
    public int queryCount(DemoEntityQuery query) {
        return demoEntityMapper.queryCount(query);
    }

    @Override
    public List<DemoEntityDTO> queryList(DemoEntityQuery query) {
        return DemoEntityConvert.dos2dtos(demoEntityMapper.queryList(query));
    }

}