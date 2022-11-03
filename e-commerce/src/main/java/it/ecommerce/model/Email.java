package it.ecommerce.model;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

public class Email {

    @Schema(example = "lahoti.ashish20@gmail.com")
    String to;
    @Schema(example = "lahoti.ashish20@gmail.com")
    String from;
    @Schema(example = "Welcome Email from CodingNConcepts")
    String subject;
    @Schema(example = "Thank you for subscribing to our channel.")
    String text;
    @Schema(example = "welcome-email.html")
    String template;
    @Schema(example = "{\n" +
        "\"name\": \"Marchetta\",\n" +
        "\"insertionDate\": \"28-12-2012\",\n" +
        "}")
    Map<String, Object> properties;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}    
    
    
}
