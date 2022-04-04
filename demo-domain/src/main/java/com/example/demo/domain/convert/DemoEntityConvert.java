package com.example.demo.domain.convert;

import com.example.demo.domain.dto.DemoEntityDTO;
import com.example.demo.domain.entity.DemoEntityDO;


import java.util.ArrayList;
import java.util.List;

/**
* @description 用户信息
* @author yeqing
* @date 2022-04-04
*/
public class DemoEntityConvert {

    public static DemoEntityDTO do2dto(DemoEntityDO item) {
        if (item == null) {
            return null;
        }

        DemoEntityDTO demoEntityDTO = new DemoEntityDTO();
        demoEntityDTO.setId(item.getId());
        demoEntityDTO.setName(item.getName());
        demoEntityDTO.setAttributes(item.getAttributes());
        demoEntityDTO.setIsDelete(item.getIsDelete());
        demoEntityDTO.setGmtCreate(item.getGmtCreate());
        demoEntityDTO.setGmtModify(item.getGmtModify());
        demoEntityDTO.setAttributeCc(item.getAttributeCc());

        return demoEntityDTO;
    }

    public static List<DemoEntityDTO> dos2dtos(List<DemoEntityDO> itemList) {
        if (itemList == null) {
            return null;
        }

        List<DemoEntityDTO> demoEntityDTOList = new ArrayList<DemoEntityDTO>(itemList.size());
        for (DemoEntityDO demoEntityDO : itemList) {
            demoEntityDTOList.add(do2dto(demoEntityDO));
        }

        return demoEntityDTOList;
    }

    public static DemoEntityDO dto2do(DemoEntityDTO item) {
        if (item == null) {
            return null;
        }

        DemoEntityDO demoEntityDO = new DemoEntityDO();
        demoEntityDO.setId(item.getId());
        demoEntityDO.setName(item.getName());
        demoEntityDO.setAttributes(item.getAttributes());
        demoEntityDO.setIsDelete(item.getIsDelete());
        demoEntityDO.setGmtCreate(item.getGmtCreate());
        demoEntityDO.setGmtModify(item.getGmtModify());
        demoEntityDO.setAttributeCc(item.getAttributeCc());

        return demoEntityDO;
    }

    public static List<DemoEntityDO> dtos2dos(List<DemoEntityDTO> itemList) {
        if (itemList == null) {
            return null;
        }

        List<DemoEntityDO> demoEntityDOList = new ArrayList<DemoEntityDO>(itemList.size());
        for (DemoEntityDTO demoEntityDTO : itemList) {
            demoEntityDOList.add(dto2do(demoEntityDTO));
        }

        return demoEntityDOList;
    }

}