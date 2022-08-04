package com.projeto.tracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("../resources/Edital/")
    private String filesPath;

    public UrlResource download(String fileName) {
        try {
            Path file = Paths.get(filesPath).resolve(fileName);
            UrlResource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Não foi possivel baixar a file");
            }
        } catch(MalformedURLException e) {
            throw new RuntimeException("ERror: " + e.getMessage());
        }
    }
}
