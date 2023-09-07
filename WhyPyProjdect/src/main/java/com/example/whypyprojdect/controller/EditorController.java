package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.service.EditorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLDecoder;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = {"http://localhost:8080"})
public class EditorController {
    final EditorService editorService;

    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @GetMapping("/api")
    public String action(@RequestParam("code") String data) throws UnsupportedEncodingException {
        log.debug(data);
        if(editorService.stringToText(URLDecoder.decode(data,"UTF-8"))){
            String ret = editorService.callPython();
            return ret;
        }else {
            return HttpStatus.FAILED_DEPENDENCY.toString();
        }
    }

}
