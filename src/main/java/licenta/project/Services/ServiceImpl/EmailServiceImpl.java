package licenta.project.Services.ServiceImpl;

import licenta.project.Services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;


    @Override
    public void send(String link, String emailUser) throws MessagingException {
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome ");

        String html = "<!doctype html>\n" +
                "<html lang=\"en\"\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <style type=\"text/css\">\n" +
                "       .container{\n" +
                "           background-color: white;\n" +
                "           max-width: 24rem;\n" +
                "           padding-left: 4rem;\n" +
                "           padding-right: 4rem;\n" +
                "           padding-top: 8rem;\n" +
                "           padding-bottom: 8rem;\n" +
                "           box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);\n" +
                "           border-radius: 25px;}\n" +
                "        .activate_button{\n" +
                "           width: 8rem;\n" +
                "           height: 3rem;\n" +
                "           border: transparent ;\n" +
                "           background-color: #10b981;\n" +
                "           padding: 8rem 8rem;\n" +
                "           font-size: medium;\n" +
                "           border-radius: 25px;\n" +
                "           color: white;\n" +
                "           box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);\n" +
                "           cursor: pointer;}\n" +
                "        :hover.activate_button{\n" +
                "           background-color: #34d399;}\n" +
                "    </style>\n" +
                "    <title>Email</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "   <div class=\"container\">\n" +
                "       <h2>Ready to get your body in his best shape?</h2>\n" +
                "       <h3>You are one step away from activating your account</h3>\n" +
                "       <a href=\"" + link + "\"><button class=\"activate_button\" name=\"activate\" id=\"activate_account\">Activate</button></a>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>\n";
        helper.setText(html, true);
        helper.setTo(emailUser);
        javaMailSender.send(mimeMessage);
    }
}
