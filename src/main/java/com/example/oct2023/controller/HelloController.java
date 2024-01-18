package com.example.oct2023.controller;

import com.example.oct2023.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/greet")
    public String sayHello(){
        log.info("REQUEST CAME THROUGH - for saying hello");
        return "WELCOME TO SPRINGBOOT FRAMEWORK";
    }

    @PostMapping("/post")
    public String postMessage(@RequestBody Message message) {
        log.debug("Message Id: "+message.getMsgid());
        log.trace("Message: "+message.getMsg());
        return "YOUR INPUT IS ACCEPTED";
    }


}


// @RestController
// @Service
// @Repository
// @Component
// @Bean

// IOC - Inversion Of Control - Creating objects/beans by Spring Container