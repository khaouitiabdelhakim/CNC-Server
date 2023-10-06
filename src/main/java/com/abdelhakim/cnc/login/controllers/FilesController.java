package com.abdelhakim.cnc.login.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
//@CrossOrigin("http://localhost:8081")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/test")
public class FilesController {


}
