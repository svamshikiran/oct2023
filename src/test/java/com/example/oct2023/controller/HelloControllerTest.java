package com.example.oct2023.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloControllerTest {

    @Autowired
    HelloController helloController;

    @Test
    void sayHello() {
        String expectedString = "WELCOME TO SPRINGBOOT FRAMEWORK";
        String actualString = helloController.sayHello();
        assertNotNull(actualString);
        assertEquals(expectedString, actualString);
    }

    @Test
    void postMessage() {
    }
}