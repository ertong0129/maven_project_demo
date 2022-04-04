package com.example.demo.controller;

import com.example.demo.business.service.DemoService;
import com.example.demo.domain.dto.DemoEntityDTO;
import com.example.demo.domain.system.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    @GetMapping("/hello")
    public Result<DemoEntityDTO> hello(@RequestParam Long id) {
        return Result.buildSuccess(demoService.getById(id));
    }


}
