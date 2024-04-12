package LOTD.project.domain;

import lombok.Getter;

@Getter
public class SendMailRequest {

    private String title;
    private String content;
    private String recipient;
}
