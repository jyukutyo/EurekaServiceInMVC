package com.jyukutyo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EurekaClientController {

    @Autowired
    NameService nameService;

    @RequestMapping("/")
    @ResponseBody
    public String example() throws Exception {
        return nameService.name();
    }

}