package com.shapestudio.common;

import com.shapestudio.common.util.ApplicationContextUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return ApplicationContextUtil.getPropertyByKey("testsss");
    }

    @GetMapping("/testList")
    public List testList() {
        return ApplicationContextUtil.getPropertyByResolveType("testpss", List.class);
    }
}
