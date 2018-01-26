package com.pangmaobao.controller;




import com.pangmaobao.annotation.AnnotationTest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: he.feng
 * @date: 13:48 2018/1/9
 * @desc:
 **/
@Controller
public class TestController {

    private static final Logger logger = LogManager.getLogger(TestController.class);


    @RequestMapping("/test")
    @AnnotationTest(value = "he.feng")
    public String  test(String name) {
        logger.info("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
        return "aaaaaaa";
    }
}
