package com.example.whypyprojdect.service;

import org.python.util.PythonInterpreter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

@Slf4j
@Service
public class EditorService {
    public boolean stringToText(String code) {
        BufferedWriter writer;
        try{
            log.debug(code);
            writer = new BufferedWriter(new FileWriter("run.py"));
            writer.write(code);
            writer.close();
            log.debug("success write");
            return true;
        }catch (IOException e){
            log.debug(e.getMessage());
            return false;
        }

    }
    public String callPython(){
        System.setProperty("python.cachedir.skip", "true");
        PythonInterpreter python = new PythonInterpreter();

        StringWriter out = new StringWriter();
        python.setOut(out);

        try {
            python.execfile("run.py");
            String result = out.toString();

            // 결과값에서 마지막 줄의 줄바꿈 제거
            if (result.endsWith("\n")) {
                result = result.substring(0, result.length() - 1);
            }

            System.out.println("result " + result);
            return result;
        }catch (Exception error){
            log.debug(error.getMessage());
            return "ERROR";
        }
    }
}
