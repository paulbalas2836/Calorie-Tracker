package licenta.project.Services;

import javax.mail.MessagingException;

public interface EmailService {
    void send(String to, String email) throws MessagingException;
}
