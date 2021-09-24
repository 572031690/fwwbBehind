package com.xhy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhy.code.PhoneCode;
import com.xhy.sendcode.SendCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CodeController {

    @RequestMapping(value = "/getCode",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> login(String phone)throws ServletException, IOException{
        Map<String,Object> map=new HashMap<String, Object>();
        String result = null;

        try {
            result = SendCode.sendCode(phone, "19469124");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper om = new ObjectMapper();
        PhoneCode phoneCode = null;
        phoneCode = om.readValue(result, PhoneCode.class);
        map.put("code", phoneCode.getObj());
        return map;
    }
}