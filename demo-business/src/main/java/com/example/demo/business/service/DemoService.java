package com.example.demo.business.service;

import com.example.demo.business.manager.DemoEntityManager;
import com.example.demo.domain.dto.DemoEntityDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author wang
 * @date 2022/4/4 3:59 PM
 */
@Service
public class DemoService {

    @Resource
    private DemoEntityManager demoEntityManager;

    public DemoEntityDTO getById(Long id) {
        return demoEntityManager.selectByPrimaryKey(id);
    }

}
