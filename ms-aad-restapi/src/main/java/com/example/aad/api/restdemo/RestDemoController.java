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

    @GetMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasAuthority('APPROLE_AdminRole')")
    public String admin() {
        return "SP has admin success.";
    }

    @GetMapping("/adminscope")
    @ResponseBody
    @PreAuthorize("hasAuthority('SCOPE_Admin')")
    public String adminscope() {
        return "SP has admin Scope.";
    }


}