package com.example.aad.api.restdemo;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RestDemoController {

    @GetMapping("/echo")
    @ResponseBody
    public String echo() {
        return "Response from Secure App";
    }

    @GetMapping("/user")
    @ResponseBody
    @PreAuthorize("hasAuthority('SCOPE_MsRestDemo.Admin')")
    public String admin() {
        return "User has admin success.";
    }

    

}