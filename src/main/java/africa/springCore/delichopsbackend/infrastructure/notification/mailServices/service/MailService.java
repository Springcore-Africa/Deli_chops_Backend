package africa.springCore.delichopsbackend.infrastructure.notification.mailServices.service;


import africa.springCore.delichopsbackend.common.data.ApiResponse;
import africa.springCore.delichopsbackend.infrastructure.notification.mailServices.domain.dtos.EmailNotificationRequest;
import jakarta.mail.MessagingException;
public interface MailService {
	
	ApiResponse sendMail(EmailNotificationRequest emailNotificationRequest) throws MessagingException;
	ApiResponse sendHtmlMail(EmailNotificationRequest emailNotificationRequest) throws MessagingException;
}
