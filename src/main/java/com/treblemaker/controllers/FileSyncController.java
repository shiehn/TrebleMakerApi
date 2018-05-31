package com.treblemaker.controllers;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileSyncController {

    @RequestMapping(value = "filesync", method = RequestMethod.POST)
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            byte[] bytes = file.getBytes();
            Path targetdir = Paths.get("/TrebleMakerApi", "src", "main", "resources", "static", "tmpaudio");
            if(!targetdir.toFile().exists()){
                targetdir.toFile().mkdirs();
            }

            Path targetFile = Paths.get(targetdir.toString(), file.getOriginalFilename());
            Files.write(targetFile, bytes);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
