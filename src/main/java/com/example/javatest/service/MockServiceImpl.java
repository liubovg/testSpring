package com.example.javatest.service;

import org.springframework.stereotype.Service;

@Service
public class MockServiceImpl implements MockService{

    @Override
    public void foo() {
        System.out.println("foo");
    }
}
