package LOTD.project.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService {

    public void sendMail(SendMailRequest request) {
        // 발신자의 네이버 이메일과 비밀번호를 설정
        final String username = "username@naver.com"; // 네이버 이메일 주소
        final String password = "password"; // 네이버 이메일 비밀번호

        String title = request.getTitle();
        String content = request.getContent();
        String recipient = request.getRecipient();


        // SMTP 서버 정보를 설정합니다.
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.naver.com"); // 네이버 SMTP 서버
        prop.put("mail.smtp.port", "465"); // SSL을 사용하는 포트
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true"); // SSL 활성화

        // 세션을 생성합니다.
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 메시지를 생성합니다.
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("username@naver.com")); // 발신자 이메일 주소
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("recipient@gmail.com")); // 수신자 이메일 주소
            message.setSubject(title); // 메일 제목
            message.setText(content); // 메일 본문

            // 메시지를 전송합니다.
            Transport.send(message);

            System.out.println("메일이 성공적으로 전송되었습니다.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}