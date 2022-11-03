package it.ecommerce.service;



import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import it.ecommerce.model.Email;
import it.ecommerce.model.PersonalData;
import it.ecommerce.model.User;
import it.ecommerce.repository.PersonalDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

	
//	@Autowired
//	private JavaMailSender mailSender;
//	
//	public void sendSimpleMail(String toEmail) {
//		SimpleMailMessage  message = new SimpleMailMessage();
//		   message.setFrom("ecommerce.application@beta80group.it");
//	        message.setTo(toEmail);
//	        message.setText("<h1>Sign up successful !</h1><br><p>this email has been used to sign up for ecommerce.application. If it wasn't you let us know</p><br><br><b>Peter</b>");
//	        message.setSubject("Ecommerce application Signup for user "+ toEmail);
//	        mailSender.send(message);
//	        System.out.println("Mail Send...");		
//	}
	@Autowired
    private JavaMailSender emailSender;
    
    @Autowired
    private SpringTemplateEngine templateEngine;
    
    @Autowired
    private PersonalDataRepository repo;
    
	private static final Logger logger = LogManager.getLogger(EmailSenderService.class);

    public void SignUpMessage(User user) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        
        Context context = new Context();
    	Email email = new Email();
    	PersonalData p = repo.findById(user.getPersonalData().getId()).get();
    	
        context.setVariable("insertionDate", LocalDate.now());
        
        context.setVariable("name", p.getName());
        
        System.out.println(context.getVariable("name"));  
        
    	email.setTemplate("WelcomeEmail.html");

        helper.setFrom("ecommerce@beta80group.it");
    	helper.setTo(user.getEmail());
    	helper.setSubject("Ecommerce application Signup for user "+ p.getName());
    	
    	String html = templateEngine.process(email.getTemplate(), context);
        helper.setText(html, true);

        logger.info("Sending email: {} with html body: {}", email, html);
        emailSender.send(message);
    }
    
}

