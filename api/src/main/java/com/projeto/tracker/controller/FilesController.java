package com.projeto.tracker.controller;

import com.projeto.tracker.model.File;
import com.projeto.tracker.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api")
@CrossOrigin("http://localhost:4200")
public class FilesController {

    @Autowired
    FilesStorageService storageService;

    @GetMapping("/files")
    public ResponseEntity<List<File>> getListFiles() {
        List<File> fileInfos = storageService.loadAll().map(path -> {
           String filename = path.getFileName().toString();
           String url = MvcUriComponentsBuilder
                   .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
            return new File(filename, url);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}