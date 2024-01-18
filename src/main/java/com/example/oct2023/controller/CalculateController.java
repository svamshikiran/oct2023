package com.example.oct2023.controller;

import com.example.oct2023.service.CalculateRestService;
import com.example.oct2023.service.CalculateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculate")
public class CalculateController {

    private static final Logger log = LoggerFactory.getLogger(CalculateController.class);

    @Autowired //Dependency Injection
    CalculateService calculateService;

    @Autowired
    CalculateRestService calculateRestService;

    @GetMapping("/add/{first}/{second}")
    public double add(@PathVariable("first") double firstNumber,
                      @PathVariable("second") double secondNumber){
        return firstNumber + secondNumber;
    }

    @GetMapping("/divide/{first}/{second}")
    public ResponseEntity<Object> divide (@PathVariable("first") double firstNumber,
                                  @PathVariable("second") double secondNumber){
        if(secondNumber == 0){
            return new ResponseEntity<>("DIVIDE BY ZERO IS NOT APPLICABLE, PLEASE CHECK AND RETRY WITH DIFFERENT INPUT", HttpStatus.BAD_REQUEST);
        }
        double result = calculateService.divide(firstNumber, secondNumber);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/add/rest/{first}/{second}")
    public double addUsingRestService(@PathVariable("first") double firstNumber,
                                      @PathVariable("second") double secondNumber){
        try{
            double result = calculateRestService.calculateAddtion(firstNumber, secondNumber);
            return result;
        }catch(Exception ex){
            log.error("Exception raised, please check the backend service");
            return 0;
        }
    }
}
