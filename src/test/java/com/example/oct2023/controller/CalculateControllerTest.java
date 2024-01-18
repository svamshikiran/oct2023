package com.example.oct2023.controller;

import com.example.oct2023.service.CalculateRestService;
import com.example.oct2023.service.CalculateService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class CalculateControllerTest {

    @InjectMocks
    CalculateController calculateController;

    @Mock
    CalculateRestService calculateRestService;

    @Mock
    CalculateService calculateService;

    @Test
    void add() {
        double fInput = 12, sInput = 13;
        double expectedResult = fInput + sInput;
        double actualResult = calculateController.add(fInput, sInput);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void divideHappyPathScenario() {
        double firstNumber = 12, secondNumber = 5;
        when(calculateService.divide(anyDouble(), anyDouble())).thenReturn(12.0/5.0);
        ResponseEntity<Object> response = calculateController.divide(firstNumber, secondNumber);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void divideExceptionScenario() {
        double firstNumber = 12, secondNumber = 0;
        ResponseEntity<Object> response = calculateController.divide(firstNumber, secondNumber);
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void addUsingRestService() {
        double firstNumber = 12, secondNumber = 13;
        //mocking scenario
        when(calculateRestService.calculateAddtion(anyDouble(), anyDouble())).thenReturn(25.0);
        //actual method call
        double actualResult = calculateController.addUsingRestService(firstNumber, secondNumber);
        assertNotNull(actualResult);
        assertEquals(25, actualResult);
    }

    @Test
    void addUsingRestServiceException() {
        double firstNumber = 12, secondNumber = 13;
        //mocking scenario
        when(calculateRestService.calculateAddtion(anyDouble(), anyDouble())).thenThrow(new RuntimeException());
        //actual method call
        double actualResult = calculateController.addUsingRestService(firstNumber, secondNumber);
        assertNotNull(actualResult);
        assertEquals(0, actualResult);
    }
}