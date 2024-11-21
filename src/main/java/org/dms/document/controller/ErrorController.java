package org.dms.document.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error")
public class ErrorController {
    @GetMapping
    public String throwError() {
        return "this is error";
    }
}
