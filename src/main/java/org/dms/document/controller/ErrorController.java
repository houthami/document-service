package org.dms.document.controller;

import org.dms.document.model.Photo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("error")
public class ErrorController {
    @GetMapping
    public String throwError() {
        return "this is error";
    }
}
