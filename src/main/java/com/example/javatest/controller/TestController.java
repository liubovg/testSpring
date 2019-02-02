package com.example.javatest.controller;

import com.example.javatest.domain.User;
import com.example.javatest.service.MockService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("root")
public class TestController {
   private final MockService mockService;

    public TestController(MockService mockService) {
     this.mockService = mockService;
    }

    public static Map<String, String> result = new HashMap<>();


    @GetMapping("test")
    public Map<String, String> test()
    {
       // Map<String, String> result = new HashMap<>();
       // result.put("name","user_name");
       // result.put("id", "user_id");

        mockService.foo();

        return result;
    }

    @PostMapping("test")
    public void receiveObject(@RequestBody User user) {

        result.put("name",user.getName());
        result.put("id", String.valueOf(user.getId()));
        System.out.println(user);
        System.out.println(result.get("name"));

        // mockService.foo();
    }


}
