# javax mail 을 활용한 메일 전송 서비스

implementation 'com.sun.mail:javax.mail:1.6.2' 을 build.gradle에 추가하여 mail 라이브러리를 사용할 수 있도록 한다.

### 1. 메일 송신에 필요한 메일 제목, 내용, 수신자 메일 주소 등을 dto 클래스에 정의해준다.
```java
@Getter
public class SendMailRequest {

    private String title;
    private String content;
    private String recipient;
}
```

### 2. Service 클래스를 작성해준다. 여기서는 TLS가 아니라 SSL을 사용하여 메일을 전송했다.

```java
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService {

    public void sendMail(SendMailRequest request) {
        // 발신자의 네이버 이메일과 비밀번호를 설정
        final String username = "jayjoy05@naver.com"; // 네이버 이메일 주소
        final String password = "tjdrhdgkwk"; // 네이버 이메일 비밀번호

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
            message.setFrom(new InternetAddress("jayjoy05@naver.com")); // 발신자 이메일 주소
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("wotjdwotjd11@gmail.com")); // 수신자 이메일 주소
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
```

### 3. Controller를 작성하여 2에서 만든 Service를 호출해주도록 한다.

```java
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail")
    public void sendMail(@RequestBody @Valid SendMailRequest request) {
        mailService.sendMail(request);

    }

}
```








