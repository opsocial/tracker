package com.projeto.tracker.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@Configuration
@PropertySource("classpath:env/javamail.properties")
public class MailConfig {

    @Autowired
    private Environment env;

}
