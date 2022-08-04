package com.projeto.tracker.email;

import com.projeto.tracker.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RequestMapping("/email")
@Controller
public class EmailSender {

    @Autowired
    SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender sender;

    @RequestMapping("/getemail")
    public @ResponseBody
    Email sendMail(@RequestBody Email email) throws Exception {
        MimeMessage message = sender.createMimeMessage();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", email.getName());

        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process("email-template", context);

        try {
            System.out.println(email.getEmail());
            helper.setFrom("admin@trackeraceleradora.com.br", "Tracker");
            helper.setTo(email.getEmail());
            helper.setText(html, true);
            helper.setSubject("Sua incrição foi enviada com sucesso!");
        } catch(javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
        return email;
    }

    @RequestMapping("/contactEmail")
    public @ResponseBody Email contactMail(@RequestBody Email email) throws Exception {
        MimeMessage message = sender.createMimeMessage();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        Session session = Session.getInstance(props);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", email.getName());
        model.put("mensagem", email.getMensagem());
        model.put("nomeEmpresa", email.getNomeEmpresa());
        model.put("email", email.getEmail());
        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process("email-send-template", context);
        try {
            helper.setFrom("admin@trackeraceleradora.com.br");
            helper.setTo("contato@trackeraceleradora.com.br");
            helper.setText(html, true);
            helper.setSubject("Email de contato de: " + email.getNomeEmpresa());
        } catch(javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
        return email;
    }
}