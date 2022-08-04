package com.projeto.tracker;

import com.projeto.tracker.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

import org.springframework.web.bind.annotation.GetMapping;
import javax.annotation.Resource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class TrackerApplication extends SpringBootServletInitializer implements CommandLineRunner {

	public static final String EMAILS_DIR = "";
	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {SpringApplication.run(TrackerApplication.class, args);}

	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}

	@GetMapping("/")
	public String home() {
		return "Welcome to tracker application!";
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TrackerApplication.class);
	}
}