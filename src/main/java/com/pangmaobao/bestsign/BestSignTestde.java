package com.pangmaobao.bestsign;

import com.pangmaobao.bestsign.service.BestSignService;
import com.pangmaobao.bestsign.service.impl.BestSignServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: he.feng
 * @date: 19:34 2018/1/26
 * @desc:
 **/
public class BestSignTestde {

    public static void main(String[] args) {
        BestSignService bestSignService = new BestSignServiceImpl();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("account","13122198120");
        param.put("name","River");
        param.put("userType","1");
        String result = bestSignService.bestSignUserReg(param);
        System.out.println(result);
    }
}
